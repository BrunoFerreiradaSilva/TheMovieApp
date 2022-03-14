package com.example.movieappferreira.model

import java.io.Serializable

data class MovieSimilar (
    val id: Int,
    val poster_path: String,
    val title: String,
):Serializable
