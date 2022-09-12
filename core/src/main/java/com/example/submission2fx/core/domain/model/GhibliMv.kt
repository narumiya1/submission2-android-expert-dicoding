package com.example.submission2fx.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class GhibliMv(
    val title: String,
    val original_title: String,
    val original_title_romanised: String,
    val image: String,
    val movie_banner: String,
    val description: String,
    val director: String,
    val producer: String,
    val release_date: String,
    val running_time: Int,
    val rt_score: Int,
    var favorite: Boolean = false

) : Parcelable