package com.example.movieappferreira.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class MoviePopular (
    val id:Int,
    val poster_path: String,
    val title: String,
    val backdrop_path: String,
    val overview: String,
    val release_date: String,
    val popularity: Float
    ): Serializable