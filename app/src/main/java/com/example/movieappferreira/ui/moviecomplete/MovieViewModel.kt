package com.example.movieappferreira.ui.moviecomplete

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappferreira.data.domain.MovieRepository
import com.example.movieappferreira.model.MoviePopular
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Network", "Caught $exception")
    }

    val popularMovieLiveData: LiveData<MutableList<MoviePopular>>
        get() = _popularMovieLiveData
    private val _popularMovieLiveData = MutableLiveData<MutableList<MoviePopular>>()

    fun getPopularMovies(page: Int) {
        viewModelScope.launch(handler) {
            val movieList = repository.getMoviePopular(page)
            _popularMovieLiveData.postValue(movieList)
        }
    }
}