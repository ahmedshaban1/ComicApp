package com.task.browse.domain

import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.Flow
// useCase to get favorite Comics from local
class GetFavoriteComicsUseCase(private val repository: ComicRepository) {
    operator fun invoke(): Flow<Resource<List<Comic>>> {
        return repository.getFavoriteComics()
    }
}
