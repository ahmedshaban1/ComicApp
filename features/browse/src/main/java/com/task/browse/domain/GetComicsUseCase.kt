package com.task.browse.domain

import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.Flow

class GetComicsUseCase(private val repository: ComicRepository) {
    operator fun invoke(): Flow<Resource<Comic>> {
        return repository.getComics()
    }
}