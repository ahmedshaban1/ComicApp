package com.task.local.dao

import androidx.room.*
import com.task.model.Comic

@Dao
interface ComicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comic: Comic): Long

    @Query("select * from Comic order by num DESC limit 1 ")
    suspend fun getComic(): Comic

    @Query("select * from Comic")
    suspend fun getAllComics(): List<Comic>
}