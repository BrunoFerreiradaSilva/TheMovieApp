package com.example.movieappferreira.data.domain

import com.example.movieappferreira.model.People

interface PeopleRepository {
    suspend fun getPeopleDetails(
        peopleID:Int
    ): People?

    suspend fun getPeopleMovieList(
        movieID: Int
    ):MutableList<People>
}