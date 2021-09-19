package com.task.browse.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.browse.databinding.FragmentSearchBinding
import com.task.browse.presentation.ui.ComicsAdapter
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.common.BaseFragment
import com.task.common.visible
import com.task.model.Comic
import com.task.remote.data.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val viewModel: ComicsViewModel by viewModel()
    private val comicsAdapter: ComicsAdapter by lazy {
        ComicsAdapter()
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun setup() {
        intRv()
        initFavoritesComicsObservable()
        viewModel.search("10")
    }

    private fun initFavoritesComicsObservable() {
        viewModel.searchComicStateFlow.asLiveData().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> it.data?.let { validateList(it) }
                Resource.Status.ERROR -> {}
            }
        })
    }

    private fun validateList(searchList: List<Comic>) {
        if(searchList.isNotEmpty())
            comicsAdapter.submitList(searchList)
        else
            binding.errorTv.visible()
    }

    private fun intRv() {
        with(binding.searchResultsRv){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = comicsAdapter
        }
    }
}