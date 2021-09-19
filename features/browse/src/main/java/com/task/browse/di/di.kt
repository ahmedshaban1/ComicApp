package com.task.browse.di

import com.task.browse.data.ComicAPIService
import com.task.browse.data.datasource.ComicRepositoryImpl
import com.task.browse.data.datasource.local.ComicLocalDataSource
import com.task.browse.data.datasource.local.ComicLocalDataSourceImpl
import com.task.browse.data.datasource.remote.ComicRemoteDataSource
import com.task.browse.data.datasource.remote.ComicRemoteDataSourceImpl
import com.task.browse.domain.ComicRepository
import com.task.browse.domain.GetComicsUseCase
import com.task.browse.domain.GetFavoriteComicsUseCase
import com.task.browse.presentation.ui.ComicsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    viewModel { ComicsViewModel(get(),get()) }

    factory {
        GetComicsUseCase(get())
    }
    factory {
        GetFavoriteComicsUseCase(get())
    }


    factory<ComicRemoteDataSource> {
        ComicRemoteDataSourceImpl(get())
    }

    factory<ComicLocalDataSource> {
        ComicLocalDataSourceImpl(get())
    }


    factory<ComicRepository> {
        ComicRepositoryImpl(get(), get())
    }

    single {
        get<Retrofit>().create(ComicAPIService::class.java)
    }
}