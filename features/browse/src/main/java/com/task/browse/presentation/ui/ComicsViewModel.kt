package com.task.browse.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.browse.domain.*
import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComicsViewModel(
    private val getComicsUseCase: GetComicsUseCase,
    private val searchComicsUseCase: SearchComicsUseCase,
    private val getFavoritesComicsUseCase: GetFavoriteComicsUseCase,
    private val getPreviousComicUseCase: GetPreviousComicUseCase,
    private val getComicByNumberUseCase: GetComicByNumberUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModel() {

    private val _getComicsStateFlow: MutableStateFlow<Resource<List<Comic>>> =
        MutableStateFlow(Resource.init())

    val comicsStateFlow: StateFlow<Resource<List<Comic>>> = _getComicsStateFlow


    private val _getFavoriteComicsStateFlow: MutableStateFlow<Resource<List<Comic>>> =
        MutableStateFlow(Resource.init())

    val favoritesComicStateFlow: StateFlow<Resource<List<Comic>>> = _getFavoriteComicsStateFlow

    private val _searchComicsStateFlow: MutableStateFlow<Resource<List<Comic>>> =
        MutableStateFlow(Resource.init())

    val searchComicStateFlow: StateFlow<Resource<List<Comic>>> = _searchComicsStateFlow

    private val _previousComicStateFlow: MutableStateFlow<Resource<Comic>> =
        MutableStateFlow(Resource.init())

    val previousComicStateFlow: StateFlow<Resource<Comic>> = _previousComicStateFlow

    private val _comicByNumberStateFlow: MutableStateFlow<Resource<Comic>> =
        MutableStateFlow(Resource.init())

    val comicByNumberComicStateFlow: StateFlow<Resource<Comic>> = _comicByNumberStateFlow

    fun getComics() {
        viewModelScope.launch {
            getComicsUseCase()
                .collect {
                    _getComicsStateFlow.value = it
                }
        }
    }

    fun getFavoritesComics() {
        viewModelScope.launch {
            getFavoritesComicsUseCase()
                .collect {
                    _getFavoriteComicsStateFlow.value = it
                }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            searchComicsUseCase(query)
                .collect {
                    _searchComicsStateFlow.value = it
                }
        }
    }

    fun getPreviousComic(comicNumber: Int) {
        viewModelScope.launch {
            getPreviousComicUseCase(comicNumber)
                .collect {
                    _previousComicStateFlow.value = it
                }
        }
    }

    fun getComicByNumber(comicNumber: Int) {
        viewModelScope.launch {
            getComicByNumberUseCase(comicNumber)
                .collect {
                    _comicByNumberStateFlow.value = it
                }
        }
    }

    fun updateFavorite(isFavorite: Boolean,comicNumber:Int) {
        updateFavoriteUseCase(isFavorite,comicNumber)
    }
}