package com.example.submission2fx.favsz

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.domain.usecase.IGhibliAppUseCase

class FavsViewModel (private val movieAppUseCase: IGhibliAppUseCase) : ViewModel() {

    fun getFavoriteMovies(): LiveData<List<GhibliMv>> =
        movieAppUseCase.getFavoriteMovies().asLiveData()

    fun setFavorite(Movie: GhibliMv, newState: Boolean) {
        movieAppUseCase.setMovieFavorite(Movie, newState)
    }
}