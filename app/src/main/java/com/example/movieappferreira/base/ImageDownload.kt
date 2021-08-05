package com.example.movieappferreira.base

import android.content.Context
import android.widget.ImageView
import coil.Coil
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.model.MoviePopular
import com.example.myapplication.R
import kotlinx.coroutines.Dispatchers

object ImageDownload {
    fun download(context: Context, moviePopular: MoviePopular, imageView: ImageView): ImageRequest {
        val imageKey = PATH_IMAGE + moviePopular.poster_path
        val imageLoader = Coil.imageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageKey)
            .crossfade(true)
            .networkCachePolicy(CachePolicy.DISABLED)
            .dispatcher(Dispatchers.Unconfined)
            .memoryCacheKey(imageKey)
            .target(imageView)
            .allowConversionToBitmap(true)
            .placeholderMemoryCacheKey(imageKey)
            .build()
        Coil.setImageLoader(imageLoader)
        return request
    }
}