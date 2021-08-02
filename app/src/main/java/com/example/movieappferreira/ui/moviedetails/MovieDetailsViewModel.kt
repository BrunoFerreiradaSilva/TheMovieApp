package com.example.movieappferreira.ui.moviedetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MovieSimilar
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.repository.MovieRepository
import kotlinx.coroutines.*

class MovieDetailsViewModel : ViewModel() {
    private val repository: MovieRepository by lazy {
        MovieRepository()
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Network", "Caught $exception")
    }

    val detailsMovieAndPeople: Pair<MutableLiveData<MovieDetails?>, MutableLiveData<MutableList<People>>>
        get() = _detailsMovieAndPeople
    private val _detailsMovieAndPeople =
        Pair(MutableLiveData<MovieDetails?>(), MutableLiveData<MutableList<People>>())

    fun getMovieDetails(movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            val movieDetails = repository.getDetailsMovie(movieID)
            detailsMovieAndPeople.first.postValue(movieDetails)

            val people = repository.getPeopleMovieList(movieID)
            detailsMovieAndPeople.second.postValue(people)
        }
    }
}