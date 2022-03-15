package com.example.movieappferreira.data.repository

import com.example.movieappferreira.data.domain.MovieRepository
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.model.MovieSimilar
import com.example.movieappferreira.rest.service.MovieService
import javax.inject.Inject


class MovieRepositoryImp @Inject constructor(private val movieService: MovieService): MovieRepository {

    override suspend fun getMoviePopular(page: Int): MutableList<MoviePopular> {
        return try {
            movieService.getPopularMovie(page).results
        } catch (e: RuntimeException) {
            mutableListOf()
        }
    }

    override suspend fun getDetailsMovie(movieID: Int): MovieDetails? {
        return try {
            movieService.getMovieDetails(movieID)
        } catch (e: RuntimeException) {
            null
        }
    }

    override suspend fun getSimilarMovies(movieID: Int): MutableList<MovieSimilar> {
        return try {
            movieService.getSimilarMovies(movieID).results
        } catch (e: RuntimeException) {
            mutableListOf()
        }
    }
}