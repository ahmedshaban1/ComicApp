package com.task.browse.domain

import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.Flow

interface ComicRepository {
    fun getLastComic() : Flow<Resource<Comic>>
    fun getFavoriteComics(): Flow<Resource<List<Comic>>>
    fun searchComics(query: String): Flow<Resource<List<Comic>>>
    fun getAllComics(): Flow<Resource<List<Comic>>>
    fun getPreviousComic(comicNumber: Int): Flow<Resource<Comic>>
    fun getComicByNumber(comicNumber: Int): Flow<Resource<Comic>>
}