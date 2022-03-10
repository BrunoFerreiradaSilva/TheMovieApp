package com.example.movieappferreira.ui.moviecomplete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappferreira.rest.repository.MovieRoomRepository
import com.example.movieappferreira.ui.people.PeopleViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import kotlin.reflect.KClass

class MovieViewModelFactory(private val repository: MovieRoomRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieRoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
