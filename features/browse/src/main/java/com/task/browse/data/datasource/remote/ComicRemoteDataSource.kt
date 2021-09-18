package com.task.browse.data.datasource.remote

import com.task.model.ComicRemote

interface ComicRemoteDataSource {
    suspend fun getLastComic() : ComicRemote
}