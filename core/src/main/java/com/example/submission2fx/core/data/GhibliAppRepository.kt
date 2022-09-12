package com.example.submission2fx.core.data

import com.example.submission2fx.core.data.source.RemoteDataSource
import com.example.submission2fx.core.data.source.localdb.LocalDataSource
import com.example.submission2fx.core.data.source.remote.netzwork.ApiGhibliResponse
import com.example.submission2fx.core.data.source.remote.response.GhibliResponse
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.domain.repository.IGhibliAppRepositoryi
import com.example.submission2fx.core.utils.AppExecutors
import com.example.submission2fx.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GhibliAppRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGhibliAppRepositoryi  {


    override fun getAllMovies(): Flow<Resource<List<GhibliMv>>> =
        object : NetworkBoundResource<List<GhibliMv>, List<GhibliResponse>>() {
            override fun loadFromDB(): Flow<List<GhibliMv>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<GhibliMv>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiGhibliResponse<List<GhibliResponse>>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: List<GhibliResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getSearchMovies(search: String): Flow<List<GhibliMv>> {
        return localDataSource.getMovieSearch(search).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteMovies(): Flow<List<GhibliMv>> {
        return localDataSource.getAllFavoriteMovies().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setMovieFavorite(movie: GhibliMv, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movieEntity, state) }

    }

}