package com.example.movieappferreira.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappferreira.rest.service.ConnectionOn

open class BaseActivity : AppCompatActivity() {

    fun onConnectionOn() {

    }

    fun onConnectionOff() {

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        onCreate()
    }

    open fun onCreate() {
        if (ConnectionOn().isConnected(this)) {
            onConnectionOn()
        }else{
            onConnectionOff()
        }
    }
}