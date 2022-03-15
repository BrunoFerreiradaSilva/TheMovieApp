package com.example.movieappferreira.database.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieappferreira.database.room.RoomRepository
import com.example.movieappferreira.model.MovieDetails
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(private val repository: RoomRepository) :
    ViewModel() {
    val allPerson: LiveData<MutableList<MovieDetails>> = repository.allPerson.asLiveData()

    fun insert(movieDetails: MovieDetails) = viewModelScope.launch {
        repository.insert(movieDetails)
    }

    fun remove(movieId: Int) = viewModelScope.launch {
        repository.remove(movieId)
    }
}