package com.task.browse.data.datasource.local

import com.task.model.Comic
// interface class for all local comic operations
interface ComicLocalDataSource {
    suspend fun getAllComics(): List<Comic>
    suspend fun getLastComic(): Comic
    suspend fun saveComic(comic: Comic)
    suspend fun getFavoritesComics(): List<Comic>
    suspend fun searchComics(query: String): List<Comic>
    suspend fun getComicByNumber(comicNumber: Int): Comic
    suspend fun updateFavorite(favorite: Boolean, comicNumber: Int)
    suspend fun checkComicFound(comicNumber: Int): Boolean
}
