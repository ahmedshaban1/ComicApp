package com.task.browse.domain

// useCase to update comic isFavourite value
class UpdateFavoriteUseCase(private val repository: ComicRepository) {
    operator fun invoke(isFavorite: Boolean, comicNumber: Int) {
        repository.updateFavorite(isFavorite, comicNumber)
    }
}
