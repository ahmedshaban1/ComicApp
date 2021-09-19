package com.task.browse.data.datasource.local

import com.task.local.dao.ComicDao
import com.task.model.Comic

class ComicLocalDataSourceImpl(private val local: ComicDao) : ComicLocalDataSource {
    override suspend fun getAllComics(): List<Comic> {
        return local.getAllComics()
    }

    override suspend fun getLastComic(): Comic {
        return local.getComic()
    }

    override suspend fun saveComic(comic: Comic) {
        val checkComic = local.getComicsCountByNumber(comic.num)
        if (checkComic == 0) {
            local.insert(comic)
        } else {
            with(comic) {
                local.update(
                    num, day,
                    img,
                    link,
                    month,
                    news,
                    safeTitle,
                    title,
                    transcript,
                    year
                )
            }

        }
    }

    override suspend fun getFavoritesComics(): List<Comic> {
        return local.getFavoritesComics()
    }

    override suspend fun searchComics(query: String): List<Comic> {
        return local.searchComics(query)
    }

    override suspend fun getComicByNumber(comicNumber: Int): Comic {
        return local.getComicByNumber(comicNumber)
    }

    override suspend fun updateFavorite(favorite: Boolean, comicNumber: Int) {
       local.updateFavorite(favorite,comicNumber)
    }
}