package com.example.movieappferreira.rest.repository

import androidx.annotation.WorkerThread
import com.example.movieappferreira.database.MovieDAO
import com.example.movieappferreira.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRoomRepository @Inject constructor(private val movieDAO: MovieDAO) {
    val allPerson: Flow<MutableList<MovieDetails>> = movieDAO.getAllPerson()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movieDetails: MovieDetails) {
        movieDAO.insertMovie(movieDetails)
    }

    suspend fun remove(movieId: Int) {
        movieDAO.removeMovie(movieId)
    }

}