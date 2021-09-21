package com.task.browse.data.datasource

import com.task.browse.data.datasource.local.ComicLocalDataSource
import com.task.browse.data.datasource.remote.ComicRemoteDataSource
import com.task.browse.domain.ComicRepository
import com.task.model.Comic
import com.task.model.ComicRemote
import com.task.model.comic.ComicMapper
import com.task.remote.NetworkBoundResource
import com.task.remote.data.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

// implementation of comic repository
@ExperimentalCoroutinesApi
class ComicRepositoryImpl(
    val remoteDataSource: ComicRemoteDataSource,
    val localDataSource: ComicLocalDataSource,
    val comicMapper: ComicMapper
) : ComicRepository {
    // get last comic from remote and save it if value of shouldSave = true
    override fun getLastComic(shouldSave: Boolean): Flow<Resource<Comic>> {
        return object : NetworkBoundResource<ComicRemote>() {
            override suspend fun remoteFetch(): ComicRemote {
                return remoteDataSource.getLastComic()
            }

            override suspend fun saveFetchResult(data: ComicRemote) {
                if (shouldSave) {
                    localDataSource.saveComic(comicMapper.mapToEntity(data))
                }
            }

            override suspend fun localFetch(): ComicRemote {
                return null!!
            }
        }.asFlow().flatMapLatest { data -> map(data) }
    }

    // get all favourite comics from local
    override fun getFavoriteComics(): Flow<Resource<List<Comic>>> {
        return object : NetworkBoundResource<List<Comic>>() {
            override suspend fun remoteFetch(): List<Comic> {
                return listOf()
            }

            override suspend fun saveFetchResult(data: List<Comic>) {
            }

            override suspend fun localFetch(): List<Comic> {
                return localDataSource.getFavoritesComics()
            }

            override fun shouldFetch() = false
        }.asFlow()
    }

    // get all comics that match query string from local
    override fun searchComics(query: String): Flow<Resource<List<Comic>>> {
        return object : NetworkBoundResource<List<Comic>>() {
            override suspend fun remoteFetch(): List<Comic> {
                return listOf()
            }

            override suspend fun saveFetchResult(data: List<Comic>) {
            }

            override suspend fun localFetch(): List<Comic> {
                return localDataSource.searchComics(query)
            }

            override fun shouldFetch() = false
        }.asFlow()
    }

    // get all comics from local
    override fun getAllComics(): Flow<Resource<List<Comic>>> {
        return object : NetworkBoundResource<List<Comic>>() {
            override suspend fun remoteFetch(): List<Comic> {
                return listOf()
            }

            override suspend fun saveFetchResult(data: List<Comic>) {
            }

            override suspend fun localFetch(): List<Comic> {
                return localDataSource.getAllComics()
            }

            override fun shouldFetch() = false
        }.asFlow()
    }

    // get new comic from remote and save it locally
    override fun getPreviousComic(comicNumber: Int): Flow<Resource<Comic>> {
        return object : NetworkBoundResource<ComicRemote>() {
            override suspend fun remoteFetch(): ComicRemote {
                return remoteDataSource.getPreviousComic(comicNumber)
            }

            override suspend fun saveFetchResult(data: ComicRemote) {
                localDataSource.saveComic(comicMapper.mapToEntity(data))
            }

            override suspend fun localFetch(): ComicRemote {
                return null!!
            }
        }.asFlow().flatMapLatest { data -> map(data) }
    }

    // get  comic by comic number from local
    override fun getComicByNumber(comicNumber: Int): Flow<Resource<Comic>> {
        return object : NetworkBoundResource<Comic>() {
            override suspend fun remoteFetch(): Comic {
                return Comic()
            }

            override suspend fun saveFetchResult(data: Comic) {
            }

            override suspend fun localFetch(): Comic {
                return localDataSource.getComicByNumber(comicNumber)
            }

            override fun shouldFetch() = false
        }.asFlow()
    }

    // just update isFavourite value by comic number locally
    override fun updateFavorite(favorite: Boolean, comicNumber: Int) {
        CoroutineScope(Main).launch {
            localDataSource.updateFavorite(favorite, comicNumber)
        }
    }

    // check if comic exists or not locally by comic number
    override suspend fun checkComicFound(comicNumber: Int): Boolean {
        return localDataSource.checkComicFound(comicNumber)
    }

    // transform data from ComicRemote to Comic
    private fun map(data: Resource<ComicRemote>): Flow<Resource<Comic>> {
        return flow {
            val comic = data.data?.let { comicMapper.mapToEntity(it) }
            emit(Resource(status = data.status, data = comic, messageType = data.messageType))
        }
    }
}
