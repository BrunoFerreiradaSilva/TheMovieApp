package com.example.movieappferreira.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieappferreira.model.Movie
import com.example.movieappferreira.model.MoviePopular
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("SELECT * from table_movie ORDER BY myId")
    fun getAllPerson(): Flow<MutableList<MoviePopular>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(moviePopular: MutableList<MoviePopular>)
}