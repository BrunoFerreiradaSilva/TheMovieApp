package com.example.movieapp.ui.moviedetails

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

    fun getMovieDetails(apiKey: String, movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            val movieDetails = repository.getDetailsMovie(apiKey, movieID)
            _detailsMovieLiveData.postValue(movieDetails)
        }
    }

    val peopleMovieLiveData: LiveData<MutableList<People>>
    get() = _peopleMovieLiveData
    private val _peopleMovieLiveData = MutableLiveData<MutableList<People>>()

    fun getPeopleMovie(apiKey: String,movieID: Int){
        CoroutineScope(Dispatchers.IO).launch(handler) {
            try{
                val people = repository.getPeopleMovie(apiKey, movieID)
                _peopleMovieLiveData.postValue(people)
            }catch (t:Throwable){
                return@launch
            }

        }
    }
    val movieSimilarDetails: LiveData<MutableList<MovieSimilar>>
        get() = _movieSimilarDetails
    private val _movieSimilarDetails = MutableLiveData<MutableList<MovieSimilar>>()

    fun getMovieSimilar(apiKey: String, movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            try {
                val movieSimilarDetails = repository.getSimilarMovies(apiKey, movieID)
                _movieSimilarDetails.postValue(movieSimilarDetails)
            }catch (t:Throwable){
                return@launch
            }

        }
    }
}