package com.example.movieappferreira.rest.repository

import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.model.MovieSimilar
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.retrofit.RetrofitInitializer

class MovieRepository {

    suspend fun getMoviePopular(page:Int): MutableList<MoviePopular>{
        return try{
            RetrofitInitializer.movieService.getPopularMovie(page).results
        }catch (e:RuntimeException){
            mutableListOf()
        }
    }

    suspend fun getDetailsMovie(movieID:Int): MovieDetails? {
        return try {
            RetrofitInitializer.movieService.getMovieDetails(movieID)
        }catch (e:RuntimeException){
            null
        }
    }

    suspend fun getPeopleDetails(peopleID:Int): People?{
        return try {
            RetrofitInitializer.movieService.getPeopleDetails(peopleID)
        }catch (e:RuntimeException){
            null
        }
    }

    suspend fun getPeopleMovieList(movieID: Int): MutableList<People>{
        return try {
            RetrofitInitializer.movieService.getPeopleMovie(movieID).cast
        }catch (e:RuntimeException){
            mutableListOf()
        }
    }

    suspend fun getSimilarMovies(movieID: Int): MutableList<MovieSimilar>{
        return try {
            RetrofitInitializer.movieService.getSimilarMovies(movieID).results
        }catch (e:RuntimeException){
            mutableListOf()
        }
    }
}
