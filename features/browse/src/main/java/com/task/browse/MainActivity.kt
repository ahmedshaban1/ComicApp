package com.task.browse

import android.view.LayoutInflater
import com.task.browse.databinding.ActivityMainBinding
import com.task.common.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setUp() {
    }


}