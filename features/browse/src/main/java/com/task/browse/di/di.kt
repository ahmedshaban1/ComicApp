package com.task.browse.di

import com.task.browse.data.ComicAPIService
import com.task.browse.data.datasource.ComicRepositoryImpl
import com.task.browse.data.datasource.local.ComicLocalDataSource
import com.task.browse.data.datasource.local.ComicLocalDataSourceImpl
import com.task.browse.data.datasource.remote.ComicRemoteDataSource
import com.task.browse.data.datasource.remote.ComicRemoteDataSourceImpl
import com.task.browse.domain.*
import com.task.browse.presentation.ui.ComicsViewModel
import com.task.model.comic.ComicMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

// koin module to provide all comic dependencies
@ExperimentalCoroutinesApi
val comicModule = module {
    viewModel { ComicsViewModel(get(), get(), get(), get(), get(), get()) }

    factory {
        GetComicsUseCase(get())
    }
    factory {
        GetFavoriteComicsUseCase(get())
    }

    factory {
        GetPreviousComicUseCase(get())
    }
    factory {
        SearchComicsUseCase(get())
    }

    factory {
        GetComicByNumberUseCase(get())
    }

    factory {
        UpdateFavoriteUseCase(get())
    }

    factory<ComicRemoteDataSource> {
        ComicRemoteDataSourceImpl(get())
    }

    factory<ComicLocalDataSource> {
        ComicLocalDataSourceImpl(get())
    }

    single<ComicRepository> {
        ComicRepositoryImpl(get(), get(), get())
    }

    single {
        ComicMapper()
    }

    single {
        get<Retrofit>().create(ComicAPIService::class.java)
    }
}
