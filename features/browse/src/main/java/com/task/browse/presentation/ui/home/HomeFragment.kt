package com.task.browse.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.task.browse.databinding.FragmentHomeBinding
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.common.BaseFragment
import com.task.remote.data.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: ComicsViewModel by viewModel()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setup() {
        viewModel.comicsStateFlow.asLiveData().observe(viewLifecycleOwner,{
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.textHome.append("Loading")
                }
                Resource.Status.SUCCESS -> {
                    binding.textHome.append(it.data?.title)
                }
                Resource.Status.ERROR -> {
                    binding.textHome.append("error")
                }
            }
        })

        viewModel.getComics()
    }

}