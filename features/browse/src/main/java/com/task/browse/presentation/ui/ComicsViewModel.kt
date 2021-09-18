package com.task.browse.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.browse.domain.GetComicsUseCase
import com.task.model.Comic
import com.task.remote.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComicsViewModel(private val getComicsUseCase: GetComicsUseCase) : ViewModel() {

    private val _getComicsStateFlow: MutableStateFlow<Resource<Comic>> =
        MutableStateFlow(Resource.loading(data = null))

    val comicsStateFlow: StateFlow<Resource<Comic>> = _getComicsStateFlow

    fun getComics() {
        viewModelScope.launch {
            getComicsUseCase()
                .collect {
                    _getComicsStateFlow.value = it
                }
        }
    }
}