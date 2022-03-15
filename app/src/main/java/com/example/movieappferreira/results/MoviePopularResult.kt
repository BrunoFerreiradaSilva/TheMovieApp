package com.example.movieappferreira.results

import com.example.movieappferreira.model.MoviePopular

data class MoviePopularResult(
    val results: MutableList<MoviePopular> = mutableListOf()
)