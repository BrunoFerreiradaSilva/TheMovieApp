package com.example.movieappferreira.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.rest.repository.MovieRoomRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieRoomViewModel @Inject constructor(private val repository: MovieRoomRepository) :
    ViewModel() {
    val allPerson: LiveData<MutableList<MovieDetails>> = repository.allPerson.asLiveData()

    fun insert(movieDetails: MovieDetails) = viewModelScope.launch {
        repository.insert(movieDetails)
    }

    fun remove(movieId: Int) = viewModelScope.launch {
        repository.remove(movieId)
    }
}