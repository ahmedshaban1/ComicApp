package com.task.browse.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
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
        initSearchInput()
    }

    private fun initSearchInput() {
        with(binding.searchET) {
            setIconifiedByDefault(true)
            isFocusable = true
            isIconified = false
            clearFocus()
            requestFocusFromTouch()
        }

        binding.searchET.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    comicsAdapter.submitList(listOf())
                }
                return false
            }
        })
    }

    private fun initFavoritesComicsObservable() {
        viewModel.searchComicStateFlow.asLiveData().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> it.data?.let { validateList(it) }
                Resource.Status.ERROR -> {
                }
            }
        })
    }

    private fun validateList(searchList: List<Comic>) {
        if (searchList.isNotEmpty())
            comicsAdapter.submitList(searchList)
        else
            binding.errorTv.visible()
    }

    private fun intRv() {
        with(binding.searchResultsRv) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = comicsAdapter
        }
    }
}