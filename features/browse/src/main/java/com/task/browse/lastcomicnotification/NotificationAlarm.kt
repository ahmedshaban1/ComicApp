package com.task.browse.lastcomicnotification

import android.app.AlarmManager
import android.content.Context
import java.util.*
import android.app.PendingIntent

import android.content.Intent




class NotificationAlarm(private val context: Context) {
    private var alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    fun setAlarm(calendar: Calendar){
        val intent = Intent(context, NotificationReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            TIME_INTERVAL, alarmIntent)

    }

    companion object{
        const val TIME_INTERVAL:Long = 1000 * 60 * 10
    }
}