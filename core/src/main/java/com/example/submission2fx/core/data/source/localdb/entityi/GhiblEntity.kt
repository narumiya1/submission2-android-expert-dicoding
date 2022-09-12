package com.example.submission2fx.core.data.source.localdb.entityi

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movieEntities")
data class GhiblEntity(
    @PrimaryKey
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "original_title")
    var original_title: String,

    @ColumnInfo(name = "original_title_romanised")
    var original_title_romanised: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "movie_banner")
    var movie_banner: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "director")
    var director: String,

    @ColumnInfo(name = "producer")
    var producer: String,

    @ColumnInfo(name = "release_date")
    var release_date: String,

    @ColumnInfo(name = "running_time")
    var running_time: Int,

    @ColumnInfo(name = "rt_score")
    var rt_score: Int,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,

    )
