package com.task.browse.lastcomicnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.JobIntentService
import java.util.*

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED")) {
            //init alarm again
            NotificationAlarm(context).setAlarm(calendar = Calendar.getInstance())
        } else {
            //init notification service
            val serviceIntent = Intent(context, LastComicService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                JobIntentService.enqueueWork(
                    context,
                    LastComicService::class.java, 101, serviceIntent
                )
            } else {
                context.startService(serviceIntent)
            }
        }
    }
}