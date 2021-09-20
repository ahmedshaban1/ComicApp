package com.task.comicapp

import android.app.Application
import com.task.browse.di.comicModule
import com.task.browse.lastcomicnotification.NotificationAlarm
import com.task.local.di.localModule
import com.task.remote.di.getRemoteModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*

@ExperimentalCoroutinesApi
class ComicApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ComicApp)
            modules(listOf(getRemoteModule("http://xkcd.com/"), localModule, comicModule))
        }

        NotificationAlarm(this).setAlarm(Calendar.getInstance())
    }

}