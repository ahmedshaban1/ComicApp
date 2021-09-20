package com.task.browse.domain

import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.Flow

class SearchComicsUseCase(private val repository: ComicRepository) {
    operator fun invoke(query: String): Flow<Resource<List<Comic>>> {
        return repository.searchComics(query)
    }
}
