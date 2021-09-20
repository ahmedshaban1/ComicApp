package com.task.browse.presentation.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.task.browse.R
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.browse.presentation.ui.favorites.data.FakeData
import com.task.model.Comic
import com.task.remote.data.Resource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest : KoinTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var viewModel: ComicsViewModel

    private val testModule = module {
        viewModel(override = true) {
            viewModel
        }
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        startKoin {
            loadKoinModules(listOf(testModule))
        }

    }

    @Test
    fun getComicsAndSuccess() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<List<Comic>>>(Resource.loading(data = null))
        every { viewModel.comicsStateFlow } returns testMutableStateFlow
        val expectedList = FakeData.fakeComics(10, true)
        launchFragmentInContainer<HomeFragment>()
        // assert
        assertDisplayed(R.id.previousComicBtn)
        assertRecyclerViewItemCount(R.id.comicsRv, expectedItemCount = 0)
        testMutableStateFlow.value = Resource.success(expectedList)
        // Assert
        assertDisplayed(R.id.comicsRv)
        assertRecyclerViewItemCount(R.id.comicsRv, expectedItemCount = expectedList.size)
        assertDisplayedAtPosition(
            R.id.comicsRv,
            0, R.id.comicTitleTv,
            expectedList[0].title
        )
    }

    @Test
    fun getComicsAndSuccessAndPreviousButtonClicked() {
        // Arrange
        val allComicsStateFlow = MutableStateFlow<Resource<List<Comic>>>(Resource.loading(data = null))
        val previousComicsStateFlow = MutableStateFlow<Resource<Comic>>(Resource.loading(data = null))
        every { viewModel.comicsStateFlow } returns allComicsStateFlow
        every { viewModel.previousComicStateFlow } returns previousComicsStateFlow
        val expectedList = FakeData.fakeComics(10, true)
        launchFragmentInContainer<HomeFragment>()
        // assert
        assertDisplayed(R.id.previousComicBtn)
        assertRecyclerViewItemCount(R.id.comicsRv, expectedItemCount = 0)
        allComicsStateFlow.value = Resource.success(expectedList)
        // Assert
        assertDisplayed(R.id.comicsRv)
        assertRecyclerViewItemCount(R.id.comicsRv, expectedItemCount = expectedList.size)
        assertDisplayedAtPosition(
            R.id.comicsRv,
            0, R.id.comicTitleTv,
            expectedList[0].title
        )

        clickOn(R.id.previousComicBtn)

        val newComic = FakeData.fakeComics(1, true)[0]
        previousComicsStateFlow.value = Resource.success(data = newComic)
        assertRecyclerViewItemCount(R.id.comicsRv, expectedItemCount = expectedList.size + 1)

        assertDisplayedAtPosition(
            R.id.comicsRv,
            expectedList.size, R.id.comicTitleTv,
            newComic.title
        )
    }

    @After
    fun teardown() {
        stopKoin()
    }

}