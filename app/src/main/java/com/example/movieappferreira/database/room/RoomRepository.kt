package com.example.movieappferreira.database.room

import androidx.annotation.WorkerThread
import com.example.movieappferreira.database.MovieDAO
import com.example.movieappferreira.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(private val movieDAO: MovieDAO) {
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