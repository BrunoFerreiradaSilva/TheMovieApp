package com.example.movieappferreira.rest.repository

import androidx.annotation.WorkerThread
import com.example.movieappferreira.database.MovieDAO
import com.example.movieappferreira.model.Movie
import com.example.movieappferreira.model.MoviePopular
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRoomRepository @Inject constructor(private val movieDAO: MovieDAO){
    val allPerson: Flow<MutableList<MoviePopular>> = movieDAO.getAllPerson()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(moviePopular: MutableList<MoviePopular>) {
        movieDAO.insertMovie(moviePopular)
    }

}