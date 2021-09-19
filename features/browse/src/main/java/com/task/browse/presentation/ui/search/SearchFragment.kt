package com.task.browse.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.task.browse.databinding.FragmentSearchBinding
import com.task.common.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun setup() {
    }
}