package com.task.browse.domain

class UpdateFavoriteUseCase(private val repository: ComicRepository) {
    operator fun invoke(isFavorite: Boolean, comicNumber: Int) {
        repository.updateFavorite(isFavorite, comicNumber)
    }
}
