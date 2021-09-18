package com.task.remote


import com.google.gson.Gson
import com.task.common.MessageType
import com.task.common.NetworkCodes.CONNECTION_ERROR
import com.task.common.NetworkCodes.GENERAL_ERROR
import com.task.common.NetworkCodes.TIMEOUT_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val messageType: MessageType) : ResultWrapper<Nothing>()
}


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ResultWrapper<T?> {
    return withContext(dispatcher) {
        try {
            val call = apiCall.invoke()
            ResultWrapper.Success(call)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    ResultWrapper.GenericError(messageType = MessageType.SnackBar(TIMEOUT_ERROR))
                }
                is IOException -> {
                    ResultWrapper.GenericError(
                        MessageType.SnackBar(CONNECTION_ERROR)
                    )
                }
                is HttpException -> {
                    val code = throwable.code()
                    ResultWrapper.GenericError(
                        MessageType.SnackBar(code, message = convertErrorBody(throwable))
                    )
                }
                else -> {
                    ResultWrapper.GenericError(
                        MessageType.Dialog(GENERAL_ERROR)
                    )
                }
            }

        }
    }
}


//for custom error body
private fun convertErrorBody(throwable: HttpException): String? {
    try {
        val json = JSONTokener(throwable.response()?.errorBody()?.string()).nextValue();
        if (json is JSONObject || json is JSONArray) {
            val errorResponse = Gson().fromJson(json.toString(), ErrorResponse::class.java)
            errorResponse?.let { return it.message }
        }
        return null

    } catch (exception: Exception) {
        return null
    }
}

class ErrorResponse(val message: String? = "")

