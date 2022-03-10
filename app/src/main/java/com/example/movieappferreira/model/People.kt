package com.example.movieappferreira.model

import java.io.Serializable

data class People(
    val id: Int,
    val profile_path: String?,
    val name: String?,
    val biography: String?,
    val place_of_birth: String?,
    val popularity: String?,
    val birthday: String?
) : Serializable
