package com.task.browse.lastcomicnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.task.browse.R
import com.task.browse.domain.ComicRepository
import com.task.model.Comic
import com.task.remote.data.Resource.Status.SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LastComicService : JobIntentService() {
    private val repo: ComicRepository by inject()

    override fun onHandleWork(intent: Intent) {
        Log.e("onHandleWork", "onHandleWork")
        CoroutineScope(Dispatchers.IO).launch {
            val lastComic = repo.getLastComic(false)
            lastComic.collect {
                if (it.status == SUCCESS) {
                    if (!repo.checkComicFound(it.data?.num ?: 0)) {
                        // fire notification
                        createNotificationChannel(this@LastComicService)
                        notifyNotification(this@LastComicService, it.data!!)
                    }
                }
            }
        }
    }

    private fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyNotification(context: Context, data: Comic) {
        with(NotificationManagerCompat.from(context)) {
            val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getString(R.string.you_have_new_comic))
                .setContentText(data.title)
                .setSmallIcon(R.drawable.ic_heart)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notify(NOTIFICATION_ID, build.build())
        }
    }

    companion object {
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_CHANNEL_ID = "1000"
        const val NOTIFICATION_CHANNEL_NAME = "Comic app"
    }
}
