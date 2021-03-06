package com.task.browse.presentation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.asLiveData
import com.task.browse.R
import com.task.browse.databinding.ActivityComicDetailsBinding
import com.task.common.*
import com.task.common.Constants.COMICNUMBER
import com.task.model.Comic
import com.task.remote.data.Resource.Status.SUCCESS
import org.koin.android.ext.android.inject

class ComicDetailsActivity : BaseActivity<ActivityComicDetailsBinding>() {
    private val viewModel: ComicsViewModel by inject()
    private val comicNumber: Int by lazy {
        intent?.getIntExtra(COMICNUMBER, 0) ?: 0
    }

    override fun setUp() {
        initActionBar()
        getComicObservable()
    }

    // show back button in actionbar
    @SuppressLint("RestrictedApi")
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
    }

    private fun getComicObservable() {
        viewModel.comicByNumberComicStateFlow.asLiveData().observe(this, {
            if (it.status == SUCCESS) {
                initViews(it.data)
            }
        })
        viewModel.getComicByNumber(comicNumber)
    }

    private fun initViews(data: Comic?) {
        data?.let {
            supportActionBar?.title = it.title
            with(binding) {
                comicCoverImg.loadImage(it.img)
                comicDescription.text = it.alt
                monthTv.text = it.month.comicValueFormat(getString(R.string.month))
                yearTv.text = it.year.comicValueFormat(getString(R.string.year))
                dayTv.text = it.day.comicValueFormat(getString(R.string.day))
                favoriteBtn.changeState(it.isFavorite)
                handleActions(it)
            }
        }
    }

    private fun handleActions(comic: Comic) {
        with(binding) {
            shareBtn.setOnClickListener {
                shareData(comic.title, comic.img, comic.alt ?: "", comic.num)
            }
            explorationBtn.setOnClickListener {
                openWebBrowser(comic.num)
            }
            favoriteBtn.setOnClickListener {
                viewModel.updateFavorite(!comic.isFavorite, comic.num)
                comic.isFavorite = !comic.isFavorite
                favoriteBtn.changeState(comic.isFavorite)
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityComicDetailsBinding
        get() = ActivityComicDetailsBinding::inflate

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
