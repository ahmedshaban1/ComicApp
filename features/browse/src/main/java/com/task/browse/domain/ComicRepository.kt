package com.task.browse.domain

import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.Flow

interface ComicRepository {
    fun getComics() : Flow<Resource<Comic>>
}