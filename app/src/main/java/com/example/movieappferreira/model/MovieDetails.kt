package com.example.movieappferreira.model

import java.io.Serializable

data class MovieDetails(
    val id: Int,
    val backdrop_path: String,
    val budget: String,
    val overview: String,
    val original_title: String,
    val genres: MutableList<Genre>,
    val release_date: String
) : Serializable
