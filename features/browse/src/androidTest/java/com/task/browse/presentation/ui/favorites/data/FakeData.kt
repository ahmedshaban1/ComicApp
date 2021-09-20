package com.task.browse.presentation.ui.favorites.data

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

    fun fakeComics(count: Int, isFavorite: Boolean): ArrayList<Comic> {
        val list: ArrayList<Comic> = arrayListOf()
        for (i in 1..count) {
            list.add(Comic(isFavorite = isFavorite, num = i, img = "https://imgs.xkcd.com/comics/rover_replies.png",title = "title"))
        }
        return list
    }
}
