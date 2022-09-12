package com.example.submission2fx.core.data.source.localdb.roomDb

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.submission2fx.core.data.source.localdb.entityi.GhiblEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao

interface MovieDao {

    @Query("SELECT * FROM movieEntities ")
    fun getMovies(): Flow<List<GhiblEntity>>

    @Query("SELECT * FROM movieEntities WHERE title LIKE '%' || :search || '%'")
    fun getSearchMovies(search: String): Flow<List<GhiblEntity>>

    @RawQuery(observedEntities = [GhiblEntity::class])
    fun getFavoriteMovies(query: SupportSQLiteQuery): Flow<List<GhiblEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<GhiblEntity>)

    @Update
    fun updateFavoriteMovie(movie: GhiblEntity)

}