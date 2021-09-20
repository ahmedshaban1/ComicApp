package com.task.browse.presentation.ui

import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.task.browse.R
import com.task.browse.databinding.ActivityBrowseBinding
import com.task.common.BaseActivity

class BrowseActivity : BaseActivity<ActivityBrowseBinding>() {
    override fun setUp() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_browse)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorites, R.id.navigation_search
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override val bindingInflater: (LayoutInflater) -> ActivityBrowseBinding
        get() = ActivityBrowseBinding::inflate
}
