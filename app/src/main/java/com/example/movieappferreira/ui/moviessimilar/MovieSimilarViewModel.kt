package com.example.movieappferreira.ui.moviessimilar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappferreira.domain.MovieRepository
import com.example.movieappferreira.model.MovieSimilar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieSimilarViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Network", "Caught $exception")
    }

    val movieSimilarDetails: LiveData<MutableList<MovieSimilar>>
        get() = _movieSimilarDetails
    private val _movieSimilarDetails = MutableLiveData<MutableList<MovieSimilar>>()

    fun getMovieSimilar(movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            try {
                val movieSimilarDetails = repository.getSimilarMovies(movieID)
                _movieSimilarDetails.postValue(movieSimilarDetails)
            }catch (t:Throwable){
                return@launch
            }

        }
    }
}