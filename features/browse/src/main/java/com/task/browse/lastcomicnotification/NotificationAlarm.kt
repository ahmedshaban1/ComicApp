package com.task.browse.lastcomicnotification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

// set repeating alarm every 1 min
class NotificationAlarm(private val context: Context) {
    private var alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    @SuppressLint("UnspecifiedImmutableFlag")
    fun setAlarm(calendar: Calendar) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            TIME_INTERVAL, alarmIntent)
    }

    companion object {
        const val TIME_INTERVAL: Long = 1000 * 60
    }
}
