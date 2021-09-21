package com.task.browse.presentation.ui.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.task.browse.R
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.browse.presentation.ui.favorites.data.FakeData.fakeComics
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
class FavoritesFragmentTest : KoinTest {
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
    fun getFavoriteComicsAndSuccess() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<List<Comic>>>(Resource.loading(data = null))
        every { viewModel.favoritesComicStateFlow } returns testMutableStateFlow
        val expectedList = fakeComics(10, true)
        // Act
        launchFragmentInContainer<FavoritesFragment>()
        // Assert
        assertRecyclerViewItemCount(R.id.favoritesRv, expectedItemCount = 0)
        // Act
        testMutableStateFlow.value = Resource.success(expectedList)
        // Assert
        assertDisplayed(R.id.favoritesRv)
        assertRecyclerViewItemCount(R.id.favoritesRv, expectedItemCount = expectedList.size)
        assertDisplayedAtPosition(
            R.id.favoritesRv,
            0, R.id.comicTitleTv,
            expectedList[0].title
        )
    }

    @After
    fun teardown() {
        stopKoin()
    }
}
