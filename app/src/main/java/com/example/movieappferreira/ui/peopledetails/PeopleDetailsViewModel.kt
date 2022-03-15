package com.example.movieappferreira.ui.peopledetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappferreira.data.domain.PeopleRepository
import com.example.movieappferreira.model.People
import kotlinx.coroutines.*
import javax.inject.Inject

class PeopleDetailsViewModel @Inject constructor(private val repository: PeopleRepository) :
    ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Network", "Caught $exception")
    }

    val peopleDetailsLiveData: LiveData<People?>
        get() = _peopleDetailsLiveData
    private val _peopleDetailsLiveData = MutableLiveData<People?>()

    fun getPeopleDetails(movieID: Int) {
        viewModelScope.launch(handler) {
            val people = repository.getPeopleDetails(movieID)
            _peopleDetailsLiveData.postValue(people)
        }
    }
}