package com.example.movieappferreira.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "table_movie")
data class MovieDetails(
    @PrimaryKey(autoGenerate = true) val myId: Int,
    @ColumnInfo(name = "movie_details")
    val id: Int,
    val backdrop_path: String,
    val poster_path:String,
    val budget: String,
    val overview: String,
    val original_title: String,
    val release_date: String
) : Serializable
