package com.example.movieappferreira.ui.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappferreira.data.domain.MovieRepository
import com.example.movieappferreira.data.domain.PeopleRepository
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.People
import kotlinx.coroutines.*
import javax.inject.Inject

class PeopleViewModel @Inject constructor(private val repository: MovieRepository, private val peopleRepository: PeopleRepository) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Network", "Caught $exception")
    }

    val detailsMovieAndPeople: LiveData<Pair<MutableLiveData<MovieDetails?>,MutableLiveData<MutableList<People>>>>
        get() = _detailsMovieAndPeople
    private val _detailsMovieAndPeople = MutableLiveData(Pair(
        MutableLiveData<MovieDetails?>(),
        MutableLiveData<MutableList<People>>()
    ))

    fun getMovieAndPeopleDetails(movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            val movieDetails = repository.getDetailsMovie(movieID)
            detailsMovieAndPeople.value?.first?.postValue(movieDetails)

            val people = peopleRepository.getPeopleMovieList(movieID)
            detailsMovieAndPeople.value?.second?.postValue(people)
        }
    }
}