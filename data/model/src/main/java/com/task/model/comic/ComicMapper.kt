package com.task.model.comic

import com.task.model.Comic
import com.task.model.ComicRemote

class ComicMapper {
    fun mapToEntity(comicRemote: ComicRemote): Comic {
        return Comic(
            day = comicRemote.day ?: "",
            img = comicRemote.img ?: "",
            link = comicRemote.link ?: "",
            month = comicRemote.month ?: "",
            news = comicRemote.news ?: "",
            num = comicRemote.num ?: 0,
            safeTitle = comicRemote.safeTitle ?: "",
            title = comicRemote.title ?: "",
            transcript = comicRemote.transcript ?: "",
            year = comicRemote.year ?: "",
            alt = comicRemote.alt?:""
        )
    }
}