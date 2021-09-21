package com.task.local.di

import com.task.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
// local module to provide room instance
val localModule = module {

    single { AppDatabase.buildDatabase(androidContext()) }
    factory { get<AppDatabase>().comicDao() }
}
