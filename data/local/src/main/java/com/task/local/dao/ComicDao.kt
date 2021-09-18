package com.task.local.dao

import androidx.room.*
import com.task.model.Comic

@Dao
interface ComicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: Comic): Long

    @Query("select * from Comic limit 1")
    suspend fun getComic(): Comic
}