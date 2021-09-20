package com.task.browse.domain

import com.task.browse.helpers.BaseComicTest
import com.task.model.Comic
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
class GetComicsUseCaseTest : BaseComicTest() {

    lateinit var sut: GetComicsUseCase

    @Before
    fun setUp() {
        sut = GetComicsUseCase(repository = comicRepository)
    }

    @Test
    fun `Given remote local, when GetComics, then get loading - success`() {
        // arrange
        val comic = Comic()

        coEvery { comicRepository.getAllComics() } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.success(data = fakeComics))
        }

        coEvery { comicRepository.getLastComic() } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.success(data = comic))
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
    fun `Given remote local, when GetComics, then get loading - error remote`() {
        // arrange
        coEvery { comicRepository.getAllComics() } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.success(data = fakeComics))
        }

        coEvery { comicRepository.getLastComic() } returns flow {
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
            collectorList.emit(Resource.success(data = fakeComics))
        }
    }

    @Test
    fun `Given remote local, when GetComics, then get loading - error remote and local`() {
        // arrange
        coEvery { comicRepository.getAllComics() } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.error(data = null, error = messageType))
        }

        coEvery { comicRepository.getLastComic() } returns flow {
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
