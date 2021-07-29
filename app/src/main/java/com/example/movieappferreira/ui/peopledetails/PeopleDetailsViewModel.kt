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


    val peopleDetailsLiveData: LiveData<People?>
        get() = _peopleDetailsLiveData
    private val _peopleDetailsLiveData = MutableLiveData<People?>()

    fun getPeopleDetails(movieID: Int){
        CoroutineScope(Dispatchers.IO).launch(handler) {
            try{
                val people = repository.getPeopleDetails(movieID)
                _peopleDetailsLiveData.postValue(people)
            }catch (t:Throwable){
                return@launch
            }

        }
    }
}