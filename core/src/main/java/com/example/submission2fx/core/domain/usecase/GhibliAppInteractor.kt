package com.example.submission2fx.core.domain.usecase

import com.example.submission2fx.core.data.Resource
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.domain.repository.IGhibliAppRepositoryi
import kotlinx.coroutines.flow.Flow

class GhibliAppInteractor(private val iGhibliAppRepositoryi: IGhibliAppRepositoryi) : IGhibliAppUseCase {

    override fun getAllMovies(): Flow<Resource<List<GhibliMv>>> = iGhibliAppRepositoryi.getAllMovies()

    override fun getSearchMovies(search: String): Flow<List<GhibliMv>> =
        iGhibliAppRepositoryi.getSearchMovies(search)

    override fun getFavoriteMovies(): Flow<List<GhibliMv>> =
        iGhibliAppRepositoryi.getFavoriteMovies()

    override fun setMovieFavorite(movie: GhibliMv, state: Boolean) {
        iGhibliAppRepositoryi.setMovieFavorite(movie, state)

    }

}