package com.example.movieappferreira.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Observers {
    val observerFavorite:LiveData<Boolean>
    get() = _observerFavorite
    private val _observerFavorite = MutableLiveData<Boolean>()
    fun getAllFavorites(value:Boolean){
        _observerFavorite.postValue(value)
    }
}