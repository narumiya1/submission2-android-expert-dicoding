package com.example.submission2fx.core.data.source.localdb.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.submission2fx.core.data.source.localdb.entityi.GhiblEntity

@Database(entities = [GhiblEntity::class], version = 4, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}