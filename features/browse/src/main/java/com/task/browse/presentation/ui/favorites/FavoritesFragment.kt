package com.task.browse.presentation.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.browse.databinding.FragmentFavoritesBinding
import com.task.browse.presentation.ui.ComicsAdapter
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.common.BaseFragment
import com.task.common.visible
import com.task.model.Comic
import com.task.remote.data.Resource.Status.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {
    private val viewModel: ComicsViewModel by viewModel()
    private val comicsAdapter: ComicsAdapter by lazy {
        ComicsAdapter()
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoritesBinding
        get() = FragmentFavoritesBinding::inflate

    override fun setup() {
        intRv()
        initFavoritesComicsObservable()
        viewModel.getFavoritesComics()
    }

    private fun intRv() {
        with(binding.favoritesRv) {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = comicsAdapter
        }
    }

    private fun initFavoritesComicsObservable() {
        viewModel.favoritesComicStateFlow.asLiveData().observe(viewLifecycleOwner, {
            when (it.status) {
                LOADING -> {
                }
                SUCCESS -> it.data?.let { validateList(it) }
                ERROR -> {
                }
            }
        })

    }

    private fun validateList(favComics: List<Comic>) {
        if (favComics.isNotEmpty())
            comicsAdapter.submitList(favComics)
        else
            binding.errorTv.visible()
    }
}