package com.task.browse.domain

import com.task.browse.helpers.BaseComicTest
import com.task.remote.data.Resource
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class SearchComicsUseCaseTest : BaseComicTest() {

    lateinit var sut: SearchComicsUseCase
    private val query = "1233"

    @Before
    fun setUp() {
        sut = SearchComicsUseCase(repository = comicRepository)
    }

    @Test
    fun `Given local query, when GetFavoriteComics, then get loading - success`() {
        // arrange
        coEvery { comicRepository.searchComics(query) } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.success(data = fakeComics))
        }
        // act
        runBlocking {
            sut(query).collect(collector = collectorList)
        }
        // assert
        coVerifyOrder {
            collectorList.emit(Resource.loading(data = null))
            collectorList.emit(Resource.success(data = fakeComics))
        }
    }

    @Test
    fun `Given local query, when GetFavoriteComics, then get loading - error`() {
        // arrange
        coEvery { comicRepository.searchComics(any()) } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.error(data = null, error = messageType))
        }
        // act
        runBlocking {
            sut(query).collect(collector = collectorList)
        }
        // assert
        coVerifyOrder {
            collectorList.emit(Resource.loading(data = null))
            collectorList.emit(Resource.error(data = null, error = messageType))
        }
    }
}
