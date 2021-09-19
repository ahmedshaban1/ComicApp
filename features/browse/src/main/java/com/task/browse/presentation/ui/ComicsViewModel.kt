package com.task.browse.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.browse.domain.GetComicsUseCase
import com.task.browse.domain.GetFavoriteComicsUseCase
import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComicsViewModel(private val getComicsUseCase: GetComicsUseCase,private val getFavoritesComicsUseCase: GetFavoriteComicsUseCase) : ViewModel() {

    private val _getComicsStateFlow: MutableStateFlow<Resource<Comic>> =
        MutableStateFlow(Resource.loading(data = null))

    val comicsStateFlow: StateFlow<Resource<Comic>> = _getComicsStateFlow


    private val _getFavoriteComicsStateFlow: MutableStateFlow<Resource<List<Comic>>> =
        MutableStateFlow(Resource.loading(data = null))

    val favoritesComicStateFlow: StateFlow<Resource<List<Comic>>> = _getFavoriteComicsStateFlow

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
}