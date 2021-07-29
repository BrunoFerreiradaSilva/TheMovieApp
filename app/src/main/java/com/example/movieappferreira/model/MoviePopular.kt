package com.example.movieappferreira.model

import java.io.Serializable

data class MoviePopular (
    val poster_path: String,
    val title: String,
    val backdrop_path: String,
    val overview: String,
    val release_date: String,
    val id: Int
    ): Serializable