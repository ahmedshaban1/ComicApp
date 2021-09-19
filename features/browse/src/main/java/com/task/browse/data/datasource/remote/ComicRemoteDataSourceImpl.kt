package com.task.browse.data.datasource.remote

import com.task.browse.data.ComicAPIService
import com.task.model.ComicRemote

class ComicRemoteDataSourceImpl(val remote: ComicAPIService):ComicRemoteDataSource {
    override suspend fun getLastComic(): ComicRemote {
        return remote.getLastComic()
    }

    override suspend fun getPreviousComic(comicNumber: Int): ComicRemote {
        return remote.getPreviousComic(comicNumber)
    }
}