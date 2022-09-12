package com.example.submission2fx.core.data.source.localdb

import com.example.submission2fx.core.data.source.localdb.entityi.GhiblEntity
import com.example.submission2fx.core.data.source.localdb.roomDb.MovieDao
import com.example.submission2fx.core.utils.SortUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn


class LocalDataSource(private val mMovieDao: MovieDao) {

    fun getAllMovies(): Flow<List<GhiblEntity>> {
        return mMovieDao.getMovies()
    }

    fun getAllFavoriteMovies(): Flow<List<GhiblEntity>> {
        val query = SortUtils.getSortedQueryFavoriteMovies()
        return mMovieDao.getFavoriteMovies(query)
    }

    fun getMovieSearch(search: String): Flow<List<GhiblEntity>> {
        return mMovieDao.getSearchMovies(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    suspend fun insertMovies(movies: List<GhiblEntity>) = mMovieDao.insertMovie(movies)

    fun setMovieFavorite(movie: GhiblEntity, newState: Boolean) {
        movie.favorite = newState
        mMovieDao.updateFavoriteMovie(movie)
    }
}