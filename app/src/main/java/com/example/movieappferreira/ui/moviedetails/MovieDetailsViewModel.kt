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

    val detailsMovieLiveData: LiveData<MovieDetails?>
        get() = _detailsMovieLiveData
    private val _detailsMovieLiveData = MutableLiveData<MovieDetails?>()

    fun getMovieDetails(movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            val movieDetails = repository.getDetailsMovie(movieID)
            _detailsMovieLiveData.postValue(movieDetails)
        }
    }

    val peopleMovieLiveData: LiveData<MutableList<People>>
        get() = _peopleMovieLiveData
    private val _peopleMovieLiveData = MutableLiveData<MutableList<People>>()

    fun getPeopleMovieList(movieID: Int){
        CoroutineScope(Dispatchers.IO).launch(handler) {
            try{
                val people = repository.getPeopleMovieList(movieID)
                _peopleMovieLiveData.postValue(people)
            }catch (t:Throwable){
                return@launch
            }

        }
    }

}