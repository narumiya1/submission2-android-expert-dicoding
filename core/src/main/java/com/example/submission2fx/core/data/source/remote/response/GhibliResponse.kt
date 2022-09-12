package com.example.submission2fx.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GhibliResponse(

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("original_title")
    val original_title: String,

    @field:SerializedName("original_title_romanised")
    val original_title_romanised: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("movie_banner")
    val movie_banner: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("director")
    val director: String,

    @field:SerializedName("producer")
    val producer: String,

    @field:SerializedName("release_date")
    val release_date: String,

    @field:SerializedName("running_time")
    val running_time: Int,

    @field:SerializedName("rt_score")
    val rt_score: Int
)
