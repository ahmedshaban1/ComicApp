package com.task.browse.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.browse.databinding.FragmentFavoritesBinding
import com.task.browse.presentation.ui.ComicDetailsActivity
import com.task.browse.presentation.ui.ComicsAdapter
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.common.BaseFragment
import com.task.common.Constants
import com.task.common.openActivity
import com.task.common.visible
import com.task.model.Comic
import com.task.remote.data.Resource.Status.SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {
    // init ComicsViewModel
    private val viewModel: ComicsViewModel by viewModel()

    // init adapter and callback
    private val comicsAdapter: ComicsAdapter by lazy {
        ComicsAdapter(interaction = object : ComicsAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Comic) {
                requireActivity().openActivity(
                    ComicDetailsActivity::class.java,
                    bundle = Bundle().apply {
                        putInt(Constants.COMICNUMBER, item.num)
                    })
            }
        })
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoritesBinding
        get() = FragmentFavoritesBinding::inflate

    override fun setup() {
        intRv()
        initFavoritesComicsObservable()
    }

    // update list after back from any place
    override fun onResume() {
        super.onResume()
        viewModel.getFavoritesComics()
    }

    private fun intRv() {
        with(binding.favoritesRv) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = comicsAdapter
        }
    }

    // handle comics status
    private fun initFavoritesComicsObservable() {
        viewModel.favoritesComicStateFlow.asLiveData().observe(viewLifecycleOwner, {
            if (it.status == SUCCESS)
                it.data?.let { favComics -> validateList(favComics) }
        })
    }

    // handle empty state
    private fun validateList(favComics: List<Comic>) {
        if (favComics.isNotEmpty())
            comicsAdapter.submitList(favComics)
        else
            binding.errorTv.visible()
    }
}
