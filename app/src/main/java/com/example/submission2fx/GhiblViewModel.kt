package com.example.submission2fx

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission2fx.core.data.Resource
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.domain.usecase.IGhibliAppUseCase

class GhiblViewModel(private val movieAppUseCase: IGhibliAppUseCase) : ViewModel() {
    fun getMovies(): LiveData<Resource<List<GhibliMv>>> {
        return movieAppUseCase.getAllMovies().asLiveData()
    }
}