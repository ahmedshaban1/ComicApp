package com.task.comicapp

import android.app.Application
import com.task.browse.di.comicModule
import com.task.browse.lastcomicnotification.NotificationAlarm
import com.task.local.di.localModule
import com.task.remote.di.getRemoteModule
import java.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class ComicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // init koin di
        startKoin {
            androidLogger()
            androidContext(this@ComicApp)
            modules(listOf(getRemoteModule("http://xkcd.com/"), localModule, comicModule))
        }
        // init notification manger to check if new comic was added
        NotificationAlarm(this).setAlarm(Calendar.getInstance())
    }
}
