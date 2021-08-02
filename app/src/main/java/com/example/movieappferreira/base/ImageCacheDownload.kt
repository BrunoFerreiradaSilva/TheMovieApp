package com.example.movieappferreira.base
import android.graphics.Bitmap
import android.util.LruCache
import android.widget.ImageView
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.squareup.picasso.Picasso

object ImageCacheDownload {
    private var memoryCache: LruCache<String, Bitmap>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, value: Bitmap): Int {
                return value.byteCount / 1024
            }
        }
    }

    fun download(imageView: ImageView, url: String) {
        val bitmap: Bitmap? = getBitMapFromMemCache(url)
        Picasso.get().load(PATH_IMAGE + url).into(imageView)
        Thread {
            try {
                memoryCache.put(url, bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getBitMapFromMemCache(imageKey: String): Bitmap? {
        return memoryCache[imageKey]
    }
}