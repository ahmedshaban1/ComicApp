package com.task.browse.presentation.ui.favorites.data

import com.task.model.Comic

// support fake data for testing
object FakeData {

    fun fakeComics(count: Int, isFavorite: Boolean): ArrayList<Comic> {
        val list: ArrayList<Comic> = arrayListOf()
        for (i in 1..count) {
            list.add(Comic(isFavorite = isFavorite, num = i, img = "https://imgs.xkcd.com/comics/rover_replies.png", title = "title"))
        }
        return list
    }
}
