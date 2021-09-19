package com.task.browse.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.browse.databinding.FragmentHomeBinding
import com.task.browse.presentation.ui.ComicDetailsActivity
import com.task.browse.presentation.ui.ComicsAdapter
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.common.BaseFragment
import com.task.common.Constants.COMICNUMBER
import com.task.common.openActivity
import com.task.model.Comic
import com.task.remote.data.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: ComicsViewModel by viewModel()
    private val comicsAdapter: ComicsAdapter by lazy {
        ComicsAdapter(interaction = object : ComicsAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Comic) {
                requireActivity().openActivity(
                    ComicDetailsActivity::class.java,
                    bundle = Bundle().apply {
                        putInt(COMICNUMBER, item.num)
                    })
            }
        })
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setup() {
        intRv()
        initGetComicsObservable()
        initGetPreviousComicLogic()
        initGetPreviousComicObservable()
        viewModel.getComics()
    }

    private fun initGetPreviousComicObservable() {
        viewModel.previousComicStateFlow.asLiveData().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    uiCommunicator?.showLoading()
                }
                Resource.Status.SUCCESS -> {
                    uiCommunicator?.hideLoading()
                    it.data?.let { comic ->
                        comicsAdapter.add(comic)
                        binding.comicsRv.smoothScrollToPosition(comicsAdapter.itemCount)
                    }
                }
                Resource.Status.ERROR -> {
                    it.messageType?.let { it1 -> uiCommunicator?.handleMessages(it1) }
                }
                Resource.Status.INIT -> {}
            }
        })
    }


    private fun initGetComicsObservable() {
        viewModel.comicsStateFlow.asLiveData().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    uiCommunicator?.showLoading()
                }
                Resource.Status.SUCCESS -> {
                    uiCommunicator?.hideLoading()
                    it.data?.let { comics ->
                        validateList(comics)
                    }
                }
                Resource.Status.ERROR -> {
                    it.messageType?.let { it1 -> uiCommunicator?.handleMessages(it1) }
                }
                Resource.Status.INIT -> {}
            }
        })
    }

    private fun intRv() {
        with(binding.comicsRv) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = comicsAdapter
        }
    }

    private fun validateList(comics: List<Comic>) {
        comicsAdapter.submitList(comics)
        initGetPreviousComicLogic()
    }

    private fun initGetPreviousComicLogic() {
        viewModel.comicsStateFlow.value.data?.let {
            var lastNumber = comicsAdapter.getLastItemNumber()
            if (lastNumber != -1) {
                binding.previousComicBtn.setOnClickListener {
                    viewModel.getPreviousComic(--lastNumber)
                }

            }
        }
    }

}