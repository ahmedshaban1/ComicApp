package com.task.browse.domain

import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.Flow
// interface class contains all comic operation
interface ComicRepository {
    fun getLastComic(shouldSave: Boolean = true): Flow<Resource<Comic>>
    fun getFavoriteComics(): Flow<Resource<List<Comic>>>
    fun searchComics(query: String): Flow<Resource<List<Comic>>>
    fun getAllComics(): Flow<Resource<List<Comic>>>
    fun getPreviousComic(comicNumber: Int): Flow<Resource<Comic>>
    fun getComicByNumber(comicNumber: Int): Flow<Resource<Comic>>
    fun updateFavorite(favorite: Boolean, comicNumber: Int)
    suspend fun checkComicFound(comicNumber: Int): Boolean
}
