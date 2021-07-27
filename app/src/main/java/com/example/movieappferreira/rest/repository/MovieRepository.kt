package com.example.movieappferreira.rest.repository

import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.model.MovieSimilar
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.retrofit.RetrofitInitializer

class MovieRepository {

    suspend fun getMoviePopular(apiKey: String, page:Int): MutableList<MoviePopular>{
        return try{
            RetrofitInitializer.movieService.getPopularMovie(page, apiKey).results
        }catch (e:RuntimeException){
            mutableListOf()
        }
    }

    suspend fun getDetailsMovie(apiKey: String,movieID:Int): MovieDetails? {
        return try {
            RetrofitInitializer.movieService.getMovieDetails(apiKey = apiKey, movieID = movieID)
        }catch (e:RuntimeException){
            null
        }
    }

    suspend fun getPeopleMovie(apiKey: String, movieID: Int): MutableList<People>{
        return try {
            RetrofitInitializer.movieService.getPeopleMovie(apiKey = apiKey,movieID = movieID).cast
        }catch (e:RuntimeException){
            mutableListOf()
        }
    }

    suspend fun getPeopleDetails(apiKey: String, peopleID:Int): People?{
        return try {
            RetrofitInitializer.movieService.getPeopleDetails(apiKey = apiKey, peopleId = peopleID)
        }catch (e:RuntimeException){
            null
        }
    }

    suspend fun getSimilarMovies(apiKey: String, movieID: Int): MutableList<MovieSimilar>{
        return try {
            RetrofitInitializer.movieService.getSimilarMovies(apiKey = apiKey, movieID = movieID).results
        }catch (e:RuntimeException){
            mutableListOf()
        }
    }
}
