package com.example.movieappferreira.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieappferreira.model.MovieDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("SELECT * from table_movie ORDER BY myId")
    fun getAllPerson(): Flow<MutableList<MovieDetails>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieDetails: MovieDetails)

    @Query("DELETE from table_movie WHERE movie_details = :movieId")
    suspend fun removeMovie(movieId: Int)

}