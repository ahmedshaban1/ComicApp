package com.task.browse.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.browse.data.datasource.FakeData.fakeComics
import com.task.browse.data.datasource.FakeData.getFakeComicRemote
import com.task.browse.data.datasource.local.ComicLocalDataSource
import com.task.browse.data.datasource.remote.ComicRemoteDataSource
import com.task.browse.domain.ComicRepository
import com.task.browse.helpers.CoroutinesMainDispatcherRule
import com.task.model.Comic
import com.task.model.comic.ComicMapper
import com.task.remote.data.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class ComicRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    private var localDataSource = mockk<ComicLocalDataSource>()
    private var remoteDataSource = mockk<ComicRemoteDataSource>()
    private var comicMapper = mockk<ComicMapper>()

    private lateinit var collector: FlowCollector<Resource<Comic>>
    private lateinit var collectorList: FlowCollector<Resource<List<Comic>>>
    private lateinit var sut: ComicRepository

    @Before
    fun setUp() {
        collector = mockk(relaxed = true)
        collectorList = mockk(relaxed = true)
        sut = ComicRepositoryImpl(remoteDataSource, localDataSource, comicMapper)
    }

    @Test
    fun `Given remote, when getLastComic, then get loading - success`() {
        // arrange
        val fakeComicRemote = getFakeComicRemote()
        val fakeComic = Comic()
        coEvery { remoteDataSource.getLastComic() } returns fakeComicRemote
        coEvery { comicMapper.mapToEntity(fakeComicRemote) } returns fakeComic
        coEvery { localDataSource.saveComic(fakeComic) } returns Unit
        // act
        runBlocking {
            sut.getLastComic().collect(collector)
        }
        // assert
        coVerifyOrder {
            collector.emit(Resource.loading(null))
            collector.emit(Resource.success(data = fakeComic))
        }
    }

    @Test
    fun `Given remote, when getLastComic, then get loading - error`() {
        // arrange
        val throwable = Throwable()
        coEvery { remoteDataSource.getLastComic() } throws throwable
        // act
        runBlocking {
            sut.getLastComic().collect(collector)
        }
        // assert
        coVerifyOrder {
            collector.emit(Resource.loading(null))
        }
        coVerify(exactly = 0) { localDataSource.saveComic(any()) }
    }

    @Test
    fun `Given local, when getFavoriteComics, then get loading - success`() {
        // arrange
        val fakeComicsLocal = fakeComics(10, true)
        coEvery { localDataSource.getFavoritesComics() } returns fakeComicsLocal
        // act
        runBlocking {
            sut.getFavoriteComics().collect(collectorList)
        }
        // assert
        coVerifyOrder {
            collectorList.emit(Resource.loading(null))
            collectorList.emit(Resource.success(data = fakeComicsLocal))
        }
        coVerify(exactly = 1) { localDataSource.getFavoritesComics() }
    }

    @Test
    fun `Given local query, when searchComics, then get loading - success`() {
        // arrange
        val fakeComicsLocal = fakeComics(10, true)
        val query = "key"
        coEvery { localDataSource.searchComics(query) } returns fakeComicsLocal
        // act
        runBlocking {
            sut.searchComics(query).collect(collectorList)
        }
        // assert
        coVerifyOrder {
            collectorList.emit(Resource.loading(null))
            collectorList.emit(Resource.success(data = fakeComicsLocal))
        }
        coVerify(exactly = 1) { localDataSource.searchComics(query) }
    }

    @Test
    fun `Given local, when getAllComics, then get loading - success`() {
        // arrange
        val fakeComicsLocal = fakeComics(10, true)
        coEvery { localDataSource.getAllComics() } returns fakeComicsLocal
        // act
        runBlocking {
            sut.getAllComics().collect(collectorList)
        }
        // assert
        coVerifyOrder {
            collectorList.emit(Resource.loading(null))
            collectorList.emit(Resource.success(data = fakeComicsLocal))
        }
        coVerify(exactly = 1) { localDataSource.getAllComics() }
    }

    @Test
    fun `Given local comicNumber, when getComicByNumber, then get loading - success`() {
        // arrange
        val fakeComicsLocal = fakeComics(1, true)[0]
        val comicNumber = 1
        coEvery { localDataSource.getComicByNumber(comicNumber) } returns fakeComicsLocal
        // act
        runBlocking {
            sut.getComicByNumber(comicNumber).collect(collector)
        }
        // assert
        coVerifyOrder {
            collector.emit(Resource.loading(null))
            collector.emit(Resource.success(data = fakeComicsLocal))
        }
        coVerify(exactly = 1) { localDataSource.getComicByNumber(comicNumber) }
    }

    @Test
    fun `Given local comicNumber, when updateFavorite, then get loading - success`() {
        // arrange
        val comicNumber = 1
        coEvery { localDataSource.updateFavorite(true, comicNumber) } returns Unit
        // act
        runBlocking {
            sut.updateFavorite(true, comicNumber)
        }
        // assert
        coVerify(exactly = 1) { localDataSource.updateFavorite(true, comicNumber) }
    }

    @Test
    fun `Given Remote, when getPreviousComic, then get loading - success`() {
        // arrange
        val fakeComicRemote = getFakeComicRemote()
        val fakeComic = Comic()
        val comicNumber = 1
        coEvery { remoteDataSource.getPreviousComic(comicNumber) } returns fakeComicRemote
        coEvery { comicMapper.mapToEntity(fakeComicRemote) } returns fakeComic
        coEvery { localDataSource.saveComic(fakeComic) } returns Unit
        // act
        runBlocking {
            sut.getPreviousComic(comicNumber).collect(collector)
        }
        // assert
        // assert
        coVerifyOrder {
            collector.emit(Resource.loading(null))
            collector.emit(Resource.success(data = fakeComic))
        }
    }

    @Test
    fun `Given Remote, when getPreviousComic, then get loading - error`() {
        // arrange
        val throwable = Throwable()
        val comicNumber = 1
        coEvery { remoteDataSource.getPreviousComic(comicNumber) } throws throwable
        // act
        runBlocking {
            sut.getPreviousComic(comicNumber).collect(collector)
        }
        // assert
        coVerifyOrder {
            collector.emit(Resource.loading(null))
        }
        coVerify(exactly = 0) { localDataSource.saveComic(any()) }
    }
}
