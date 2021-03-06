package com.task.browse.data

import com.task.model.ComicRemote
import retrofit2.http.GET
import retrofit2.http.Path

// comic api service that has all remote operations
interface ComicAPIService {
    @GET("info.0.json")
    suspend fun getLastComic(): ComicRemote

    @GET("{comicNumber}/info.0.json")
    suspend fun getPreviousComic(@Path("comicNumber") comicNumber: Int): ComicRemote
}
