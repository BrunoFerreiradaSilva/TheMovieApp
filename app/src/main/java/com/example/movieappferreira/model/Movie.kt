package com.example.movieappferreira.model

import java.io.Serializable

data class Movie(
    val poster_path: String,
    val original_title: String
) : Serializable