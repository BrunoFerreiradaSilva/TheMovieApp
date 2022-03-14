package com.example.movieappferreira.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappferreira.rest.repository.MovieRoomRepository

class MovieViewModelFactory(private val repository: MovieRoomRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieRoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
