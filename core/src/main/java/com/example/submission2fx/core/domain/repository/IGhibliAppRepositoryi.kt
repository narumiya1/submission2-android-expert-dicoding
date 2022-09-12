package com.example.submission2fx.core.domain.repository

import com.example.submission2fx.core.data.Resource
import com.example.submission2fx.core.domain.model.GhibliMv
import kotlinx.coroutines.flow.Flow

interface IGhibliAppRepositoryi {

    fun getAllMovies(): Flow<Resource<List<GhibliMv>>>
    fun getSearchMovies(search: String): Flow<List<GhibliMv>>
    fun getFavoriteMovies(): Flow<List<GhibliMv>>

    fun setMovieFavorite(movie: GhibliMv, state: Boolean)

}