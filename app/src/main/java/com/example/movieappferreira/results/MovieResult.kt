package com.example.movieappferreira.results

import com.example.movieappferreira.model.Movie

data class MovieResult(
    val results: MutableList<Movie> = mutableListOf()
)