package com.task.browse.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.task.browse.databinding.FragmentHomeBinding
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.remote.data.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: ComicsViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        viewModel.comicsStateFlow.asLiveData().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    textView.append("Loading")
                }
                Resource.Status.SUCCESS -> {
                    textView.append(it.data?.title)
                }
                Resource.Status.ERROR -> {
                    textView.append("error")
                }
            }
        })

        viewModel.getComics()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}