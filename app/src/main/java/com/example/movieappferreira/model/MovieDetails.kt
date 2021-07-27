package com.example.movieappferreira.model

import java.io.Serializable

data class MovieDetails(
    val backdrop_path: String = "",
    val budget: String = "",
    var id:Int = 0,
    val overview: String = "",
    val original_title:String = "",
    val genres: MutableList<Genre> = mutableListOf(),
    val release_date: String = ""

):Serializable
