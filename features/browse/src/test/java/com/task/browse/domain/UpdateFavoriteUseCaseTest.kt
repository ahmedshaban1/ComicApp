package com.task.browse.domain

import com.task.browse.helpers.BaseComicTest
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class UpdateFavoriteUseCaseTest : BaseComicTest() {

    lateinit var sut: UpdateFavoriteUseCase

    @Before
    fun setUp() {
        sut = UpdateFavoriteUseCase(repository = comicRepository)
    }

    @Test
    fun `Given local isFavorite-ComicNumber, when GetFavoriteComics, then get loading - success`() {
        // arrange
        coEvery { comicRepository.updateFavorite(any(), any()) } returns Unit
        // act
        runBlocking {
            sut(true, comicNumber)
        }
        // assert
        coVerify(exactly = 1) { comicRepository.updateFavorite(true, comicNumber) }
    }
}
