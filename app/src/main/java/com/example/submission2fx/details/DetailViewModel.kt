package com.example.submission2fx.details

import androidx.lifecycle.ViewModel
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.domain.usecase.IGhibliAppUseCase


class DetailViewModel(private val movieAppUseCase: IGhibliAppUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: GhibliMv, newStatus: Boolean) {
        movieAppUseCase.setMovieFavorite(movie, newStatus)
    }
}