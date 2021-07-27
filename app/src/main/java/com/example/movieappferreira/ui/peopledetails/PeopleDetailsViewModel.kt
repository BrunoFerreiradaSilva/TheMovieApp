package com.example.movieappferreira.ui.peopledetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.repository.MovieRepository
import kotlinx.coroutines.*

class PeopleDetailsViewModel: ViewModel() {
    private val repository: MovieRepository by lazy {
        MovieRepository()
    }
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Network", "Caught $exception")
    }

    val detailsPeopleLiveData: LiveData<People?>
        get() = _detailsPeopleLiveData
    private val _detailsPeopleLiveData = MutableLiveData<People?>()

    fun getMovieDetails(apiKey: String, movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            try {
                val peopleDetails = repository.getPeopleDetails(apiKey, movieID)
                _detailsPeopleLiveData.postValue(peopleDetails)
            }catch (t:Throwable){
                return@launch
            }

        }
    }
}