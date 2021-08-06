package com.example.movieappferreira.ui.moviecomplete

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.rest.repository.MovieRoomRepository
import kotlinx.coroutines.launch

class MovieRoomViewModel(private val repository: MovieRoomRepository): ViewModel() {
    val allPerson: LiveData<MutableList<MoviePopular>> = repository.allPerson.asLiveData()

    fun insert(moviePopular: MutableList<MoviePopular>) = viewModelScope.launch {
        repository.insert(moviePopular)
    }
}