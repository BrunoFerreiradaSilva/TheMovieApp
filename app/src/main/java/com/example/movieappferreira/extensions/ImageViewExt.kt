package com.example.movieappferreira.extensions

import android.widget.ImageView
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(path: String) {
    Picasso.get().load("${PATH_IMAGE}$path")
        .into(this)
}