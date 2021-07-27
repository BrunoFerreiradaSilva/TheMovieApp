package com.example.movieappferreira.results

import com.example.movieappferreira.model.People

data class PeopleMovieResult(
    val id: Int = 0,
    val cast: MutableList<People> = mutableListOf()
)
