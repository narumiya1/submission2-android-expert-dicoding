package com.example.submission2fx.core.data.source.remote.netzwork

import com.example.submission2fx.core.data.source.remote.response.GhibliResponse
import retrofit2.http.GET

interface ApiGhibli {
    @GET("films/")
    suspend fun getAllMovies(): List<GhibliResponse>
}