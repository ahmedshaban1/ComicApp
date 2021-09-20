package com.task.browse.helpers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.browse.domain.ComicRepository
import com.task.browse.domain.GetComicByNumberUseCase
import com.task.model.Comic
import com.task.remote.data.Resource
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseComicTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    var comicNumber = 1

    var comicRepository = mockk<ComicRepository>()
    var collector: FlowCollector<Resource<Comic>> = mockk(relaxed = true)
    var collectorList: FlowCollector<Resource<List<Comic>>> = mockk(relaxed = true)

}