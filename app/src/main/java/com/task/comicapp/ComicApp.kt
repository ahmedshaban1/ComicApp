package com.task.comicapp

import android.app.Application
import com.task.browse.di.homeModule
import com.task.local.di.localModule
import com.task.remote.di.getRemoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
class ComicApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ComicApp)
            modules(listOf(getRemoteModule("http://xkcd.com/"),localModule,homeModule))
        }
    }

}