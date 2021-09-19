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

    @Query("select * from Comic where isFavorite=1")
    suspend fun getFavoritesComics(): List<Comic>

    @Query("select COUNT(num) from Comic where num=:comicNumber limit 1")
    suspend fun getComicByNumber(comicNumber: Int): Int

    @Query("UPDATE comic set day=:day , img=:img,link=:link,month=:month,news=:news,safeTitle=:safeTitle,title=:title,transcript=:transcript,year=:year where num=:comicNumber")
    suspend fun update(
        comicNumber: Int, day: String,
        img: String,
        link: String,
        month: String,
        news: String,
        safeTitle: String,
        title: String,
        transcript: String,
        year: String
    )

}