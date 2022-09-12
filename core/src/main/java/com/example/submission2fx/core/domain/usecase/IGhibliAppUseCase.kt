package com.example.submission2fx.core.domain.usecase

import com.example.submission2fx.core.data.Resource
import com.example.submission2fx.core.domain.model.GhibliMv
import kotlinx.coroutines.flow.Flow

interface IGhibliAppUseCase {
    fun getAllMovies(): Flow<Resource<List<GhibliMv>>>
    fun getSearchMovies(search: String): Flow<List<GhibliMv>>

    fun getFavoriteMovies(): Flow<List<GhibliMv>>

    fun setMovieFavorite(movie: GhibliMv, state: Boolean)

}