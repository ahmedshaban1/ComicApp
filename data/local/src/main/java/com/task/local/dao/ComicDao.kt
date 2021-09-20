package com.task.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.model.Comic

@Dao
interface ComicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comic: Comic): Long

    @Query("select * from Comic order by num DESC limit 1 ")
    suspend fun getComic(): Comic

    @Query("select * from Comic order by num DESC")
    suspend fun getAllComics(): List<Comic>

    @Query("select * from Comic where isFavorite=1 order by num DESC")
    suspend fun getFavoritesComics(): List<Comic>

    @Query("select COUNT(num) from Comic where num=:comicNumber limit 1")
    suspend fun getComicsCountByNumber(comicNumber: Int): Int

    @Query("select * from Comic where num=:comicNumber limit 1")
    suspend fun getComicByNumber(comicNumber: Int): Comic

    @Query("UPDATE comic set day=:day , img=:img,link=:link,month=:month,news=:news,safeTitle=:safeTitle,title=:title,transcript=:transcript,year=:year where num=:comicNumber")
    suspend fun update(
        comicNumber: Int,
        day: String,
        img: String,
        link: String,
        month: String,
        news: String,
        safeTitle: String,
        title: String,
        transcript: String,
        year: String
    )

    @Query("select * from Comic where num  LIKE '%' || :query || '%' or  title  LIKE '%' || :query || '%' or safeTitle  LIKE '%' || :query || '%' order by num DESC")
    suspend fun searchComics(query: String): List<Comic>

    @Query("UPDATE comic set isFavorite=:favorite  where num=:comicNumber")
    suspend fun updateFavorite(favorite: Boolean, comicNumber: Int)
}
