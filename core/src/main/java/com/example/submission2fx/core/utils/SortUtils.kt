package com.example.submission2fx.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    fun getSortedQueryFavoriteMovies(): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("SELECT * FROM movieEntities where favorite = 1")
    
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}