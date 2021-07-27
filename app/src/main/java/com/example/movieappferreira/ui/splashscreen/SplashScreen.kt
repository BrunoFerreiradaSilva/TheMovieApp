package com.example.movieappferreira.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappferreira.ui.moviecomplete.MoviePopularCompleteActivity
import com.example.myapplication.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handle = Handler()
        handle.postDelayed({
            showSplashScreen()
        }, 2000)

    }

    private fun showSplashScreen() {
        val intent = Intent(this@SplashScreen, MoviePopularCompleteActivity::class.java)
        startActivity(intent)
    }
}