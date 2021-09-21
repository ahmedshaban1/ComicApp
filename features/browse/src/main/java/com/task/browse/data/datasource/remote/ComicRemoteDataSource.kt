package com.task.browse.data.datasource.remote

import com.task.model.ComicRemote

// interface class for all remote comic operations
interface ComicRemoteDataSource {
    suspend fun getLastComic(): ComicRemote
    suspend fun getPreviousComic(comicNumber: Int): ComicRemote
}
