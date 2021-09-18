package com.task.browse.data.datasource.local

import com.task.local.dao.ComicDao
import com.task.model.Comic

class ComicLocalDataSourceImpl(private val local:ComicDao):ComicLocalDataSource {
    override suspend fun getAllComics(): List<Comic> {
       return local.getAllComics()
    }

    override suspend fun getLastComic(): Comic {
        return local.getComic()
    }

    override suspend fun saveComic(comic: Comic) {
        local.insert(comic)
    }
}