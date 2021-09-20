package com.task.browse.domain

import com.task.browse.helpers.BaseComicTest
import com.task.common.MessageType
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
class GetComicByNumberUseCaseTest : BaseComicTest() {

    lateinit var sut: GetComicByNumberUseCase

    @Before
    fun setUp() {
        sut = GetComicByNumberUseCase(repository = comicRepository)
    }

    @Test
    fun `Given local comicNumber, when GetComicByNumber, then get loading - success`() {
        //arrange
        val comic = Comic()
        coEvery { comicRepository.getComicByNumber(any()) } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.success(data = comic))
        }
        //act
        runBlocking {
            sut(comicNumber).collect(collector = collector)
        }
        //assert
        coVerifyOrder {
            collector.emit(Resource.loading(data = null))
            collector.emit(Resource.success(data = comic))
        }
    }

    @Test
    fun `Given local comicNumber, when GetComicByNumber, then get loading - error`() {
        //arrange
        val messageType = MessageType.Toast(0, null)
        coEvery { comicRepository.getComicByNumber(any()) } returns flow {
            emit(Resource.loading(data = null))
            emit(Resource.error(data = null, error = messageType))
        }
        //act
        runBlocking {
            sut(comicNumber).collect(collector = collector)
        }
        //assert
        coVerifyOrder {
            collector.emit(Resource.loading(data = null))
            collector.emit(Resource.error(data = null, error = messageType))
        }
    }
}