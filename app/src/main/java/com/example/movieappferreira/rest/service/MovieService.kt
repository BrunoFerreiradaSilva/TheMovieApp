package com.example.movieappferreira.rest.service


import com.example.movieappferreira.base.Constants
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.People
import com.example.movieappferreira.results.MoviePopularResult
import com.example.movieappferreira.results.MovieSimilarResults
import com.example.movieappferreira.results.PeopleMovieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.PRIMARY_KEY
    ): MoviePopularResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = Constants.PRIMARY_KEY
    ): MovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getPeopleMovie(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = Constants.PRIMARY_KEY
    ): PeopleMovieResult

    @GET("person/{person_id}")
    suspend fun getPeopleDetails(
        @Path("person_id") peopleId: Int,
        @Query("api_key") apiKey: String = Constants.PRIMARY_KEY
    ): People

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = Constants.PRIMARY_KEY
    ): MovieSimilarResults
}