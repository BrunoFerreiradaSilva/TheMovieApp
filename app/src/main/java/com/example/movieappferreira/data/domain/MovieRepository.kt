package com.example.movieappferreira.data.domain

import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.model.MovieSimilar

interface MovieRepository {
    suspend fun getMoviePopular(
        page: Int
    ): MutableList<MoviePopular>

    suspend fun getDetailsMovie(
        movieID: Int
    ): MovieDetails?

    suspend fun getSimilarMovies(
        movieID: Int
    ): MutableList<MovieSimilar>
}