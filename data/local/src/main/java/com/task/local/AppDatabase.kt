package com.task.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.local.dao.ComicDao
import com.task.model.Comic

@Database(entities = [Comic::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comicDao(): ComicDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "appdatabase.db"
            ).build()
    }
}
