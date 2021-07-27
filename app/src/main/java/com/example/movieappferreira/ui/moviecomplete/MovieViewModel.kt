package com.example.movieappferreira.ui.moviecomplete

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.rest.repository.MovieRepository
import kotlinx.coroutines.*

class MovieViewModel : ViewModel() {
    private val repository: MovieRepository by lazy {
        MovieRepository()
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Network", "Caught $exception")
    }

    val popularMovieLiveData: LiveData<MutableList<MoviePopular>>
        get() = _popularMovieLiveData
    private val _popularMovieLiveData = MutableLiveData<MutableList<MoviePopular>>()

    fun getPopularMovies(apiKey: String, page: Int) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            try {
                val movieList = repository.getMoviePopular(apiKey, page)
                _popularMovieLiveData.postValue(movieList)
            } catch (t: Throwable) {
                return@launch
            }

        }
    }
}