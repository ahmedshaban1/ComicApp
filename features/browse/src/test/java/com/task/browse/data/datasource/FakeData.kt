package com.task.browse.data.datasource

import com.task.model.Comic
import com.task.model.ComicRemote

object FakeData {

    fun getFakeComicRemote(): ComicRemote {
        return ComicRemote(
            alt = "alt",
            safeTitle = "SafeTitle",
            title = "Title",
            news = "",
            year = "2021",
            day = "0",
            transcript = "",
            month = "9",
            img = "",
            link = "",
            num = 0
        )
    }

    fun fakeFetComics(count:Int,isFavorite:Boolean): ArrayList<Comic> {
        val list:ArrayList<Comic> = arrayListOf()
        for (i in 0..count){
            list.add(Comic(isFavorite = isFavorite , num = 1))
        }
        return list
    }



}