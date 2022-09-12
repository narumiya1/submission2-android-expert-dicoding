package com.example.submission2fx.core.utils

import com.example.submission2fx.core.data.source.localdb.entityi.GhiblEntity
import com.example.submission2fx.core.data.source.remote.response.GhibliResponse
import com.example.submission2fx.core.domain.model.GhibliMv

object DataMapper {

    fun mapMovieResponsesToEntities(input: List<GhibliResponse>): List<GhiblEntity> {
        val movieList = ArrayList<GhiblEntity>()
        input.map {
            val movie = GhiblEntity(
                it.title,
                it.original_title,
                it.original_title_romanised,
                it.image,
                it.movie_banner,
                it.description,
                it.director,
                it.producer,
                it.release_date,
                it.running_time,
                it.rt_score,
                favorite = false,
                )
            movieList.add(movie)
        }
        return movieList
    }


    fun mapEntitiesToDomain(input: List<GhiblEntity>): List<GhibliMv> {
        return input.map {
            GhibliMv(
                it.title,
                it.original_title,
                it.original_title_romanised,
                it.image,
                it.movie_banner,
                it.description,
                it.director,
                it.producer,
                it.release_date,
                it.running_time,
                it.rt_score,
                favorite = it.favorite,
                )
        }
    }

    fun mapDomainToEntity(input: GhibliMv): GhiblEntity {
        return GhiblEntity(
            input.title,
            input.original_title,
            input.original_title_romanised,
            input.image,
            input.movie_banner,
            input.description,
            input.producer,
            input.director,
            input.release_date,
            input.running_time,
            input.rt_score,
            favorite = input.favorite,
            )
    }
}