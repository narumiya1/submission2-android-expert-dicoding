package com.example.submission2fx.core.data.source

import android.util.Log
import com.example.submission2fx.core.data.source.remote.netzwork.ApiGhibli
import com.example.submission2fx.core.data.source.remote.netzwork.ApiGhibliResponse
import com.example.submission2fx.core.data.source.remote.response.GhibliResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class RemoteDataSource(private val apiService : ApiGhibli) {
  
    suspend fun getMovies(): Flow<ApiGhibliResponse<List<GhibliResponse>>> {
        return flow {
            try {
                val response = apiService.getAllMovies()
                Log.d("",""+response)
                if (response.isNotEmpty()) {
                    emit(ApiGhibliResponse.Success(response))
                } else {
                    emit(ApiGhibliResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiGhibliResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}