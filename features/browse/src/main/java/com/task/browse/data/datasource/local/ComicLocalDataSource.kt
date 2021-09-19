package com.task.browse.data.datasource.local

import com.task.model.Comic

interface ComicLocalDataSource {
    suspend fun getAllComics(): List<Comic>
    suspend fun getLastComic(): Comic
    suspend fun saveComic(comic: Comic)
    suspend fun getFavoritesComics(): List<Comic>
    suspend fun searchComics(query: String): List<Comic>
}