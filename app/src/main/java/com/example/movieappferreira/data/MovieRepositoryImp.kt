package com.example.movieappferreira.data

import com.example.movieappferreira.domain.MovieRepository
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.model.MovieSimilar
import com.example.movieappferreira.rest.retrofit.RetrofitInitializer


class MovieRepositoryImp  : MovieRepository {

    override suspend fun getMoviePopular(page: Int): MutableList<MoviePopular> {
        return try{
            RetrofitInitializer.movieService.getPopularMovie(page).results
        }catch (e:RuntimeException){
            mutableListOf()
        }
    }

    override suspend fun getDetailsMovie(movieID: Int): MovieDetails? {
        return try {
            RetrofitInitializer.movieService.getMovieDetails(movieID)
        }catch (e:RuntimeException){
            null
        }
    }

    override suspend fun getSimilarMovies(movieID: Int): MutableList<MovieSimilar> {
        return try {
            RetrofitInitializer.movieService.getSimilarMovies(movieID).results
        }catch (e:RuntimeException){
            mutableListOf()
        }
    }
}