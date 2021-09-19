package com.task.remote.data

import com.task.common.MessageType


data class Resource<out T>(val status: Status, val data: T?, val messageType: MessageType?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: MessageType, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
        fun <T> init(): Resource<T> {
            return Resource(
                Status.INIT,
                null,
                null
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        INIT
    }
}