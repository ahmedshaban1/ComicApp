package com.task.comicapp

import android.view.LayoutInflater
import com.task.comicapp.databinding.ActivityMainBinding
import com.task.common.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setUp() {


    }


}