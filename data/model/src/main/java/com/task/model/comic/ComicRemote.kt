package com.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// Model
data class ComicRemote(
    @SerializedName("alt")
    var alt: String?,
    @SerializedName("day")
    var day: String?,
    @SerializedName("img")
    var img: String?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("month")
    var month: String?,
    @SerializedName("news")
    var news: String?,
    @SerializedName("num")
    var num: Int?,
    @SerializedName("safe_title")
    var safeTitle: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("transcript")
    var transcript: String?,
    @SerializedName("year")
    var year: String?
)

// Database Entity
@Entity(tableName = "comic")
data class Comic(
    var day: String = "",
    var img: String = "",
    var link: String = "",
    var month: String = "",
    var news: String = "",
    @PrimaryKey var num: Int = 0,
    var safeTitle: String = "",
    var title: String = "",
    var transcript: String = "",
    var year: String = "",
    var isFavorite: Boolean = false,
    var alt: String? = null
)
