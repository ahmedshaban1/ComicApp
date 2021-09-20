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
class GetFavoriteComicsUseCaseTest : BaseComicTest() {
    lateinit var sut: GetFavoriteComicsUseCase

    @Before
    fun setUp() {
        sut = GetFavoriteComicsUseCase(repository = comicRepository)
    }

    @Test
    fun `Given local, when GetFavoriteComics, then get loading - success`() {
        // arrange
        coEvery { comicRepository.getFavoriteComics() } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.success(data = fakeComics))
        }
        // act
        runBlocking {
            sut().collect(collector = collectorList)
        }
        // assert
        coVerifyOrder {
            collectorList.emit(Resource.loading(data = null))
            collectorList.emit(Resource.success(data = fakeComics))
        }
    }

    @Test
    fun `Given local comicNumber, when GetComicByNumber, then get loading - error`() {
        // arrange
        coEvery { comicRepository.getFavoriteComics() } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.error(data = null, error = messageType))
        }
        // act
        runBlocking {
            sut().collect(collector = collectorList)
        }
        // assert
        coVerifyOrder {
            collectorList.emit(Resource.loading(data = null))
            collectorList.emit(Resource.error(data = null, error = messageType))
        }
    }
}
