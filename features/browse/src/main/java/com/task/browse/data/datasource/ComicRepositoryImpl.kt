package com.task.browse.data.datasource

import com.task.browse.data.datasource.local.ComicLocalDataSource
import com.task.browse.data.datasource.remote.ComicRemoteDataSource
import com.task.browse.domain.ComicRepository
import com.task.model.Comic
import com.task.model.ComicRemote
import com.task.model.comic.ComicMapper
import com.task.remote.NetworkBoundResource
import com.task.remote.data.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class ComicRepositoryImpl(
    val remoteDataSource: ComicRemoteDataSource,
    val localDataSource: ComicLocalDataSource
) : ComicRepository {
    override fun getComics(): Flow<Resource<Comic>> {
        return object : NetworkBoundResource<ComicRemote>() {
            override suspend fun remoteFetch(): ComicRemote {
                return remoteDataSource.getLastComic()
            }

            override suspend fun saveFetchResult(data: ComicRemote) {
                localDataSource.saveComic(ComicMapper().mapToEntity(data))
            }

            override suspend fun localFetch(): ComicRemote {
                return null!!
            }

        }.asFlow().flatMapLatest {data->map(data) }
    }

    override fun getFavoriteComics(): Flow<Resource<List<Comic>>> {
        return object : NetworkBoundResource<List<Comic>>(){
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

    override fun searchComics(query: String): Flow<Resource<List<Comic>>> {
        return object : NetworkBoundResource<List<Comic>>(){
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


    private fun map(data: Resource<ComicRemote>): Flow<Resource<Comic>> {
        return flow {
            val comic =  data.data?.let { ComicMapper().mapToEntity(it)}
            emit(Resource(status = data.status, data = comic, messageType = data.messageType))
        }
    }

}