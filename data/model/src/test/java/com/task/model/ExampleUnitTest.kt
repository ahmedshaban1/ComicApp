package com.task.model

import com.task.model.comic.ComicMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class ComicMapperTest {
    private val fakeComic = ComicRemote(
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
        num = 1
    )

    @Test
    fun `Given comic remote, when mapToEntity, then get Comic `() {

        //act
        val results = ComicMapper().mapToEntity(comicRemote = fakeComic)
        //assert
        assertEquals("alt", results.alt)
        assertEquals("SafeTitle", results.safeTitle)
        assertEquals("Title", results.title)
        assertEquals("", results.news)
        assertEquals("2021", results.year)
        assertEquals("0", results.day)
        assertEquals("", results.transcript)
        assertEquals("9", results.month)
        assertEquals("", results.img)
        assertEquals("", results.link)
        assertEquals(1, results.num)

    }
}
