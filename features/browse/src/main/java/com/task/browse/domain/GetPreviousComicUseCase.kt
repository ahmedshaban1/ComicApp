package com.task.browse.domain

import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.Flow

// useCase to get previous Comic from remote
class GetPreviousComicUseCase(private val repository: ComicRepository) {
    operator fun invoke(comicNumber: Int): Flow<Resource<Comic>> {
        return repository.getPreviousComic(comicNumber)
    }
}
