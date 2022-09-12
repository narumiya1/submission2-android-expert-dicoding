package com.example.submission2fx.core.data.source.remote.netzwork

sealed class ApiGhibliResponse <out R> {
    data class Success<out T>(val data: T) : ApiGhibliResponse<T>()
    data class Error(val errorMessage: String) : ApiGhibliResponse<Nothing>()
    object Empty : ApiGhibliResponse<Nothing>()
}