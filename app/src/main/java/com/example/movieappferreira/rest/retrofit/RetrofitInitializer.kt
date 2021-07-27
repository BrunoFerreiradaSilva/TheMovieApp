package com.example.movieappferreira.rest.retrofit

import com.example.movieappferreira.base.Constants.BASE_URL
import com.example.movieappferreira.rest.service.MovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInitializer {
    val movieService: MovieService by lazy {
        retrofit().create(MovieService::class.java)
    }

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}