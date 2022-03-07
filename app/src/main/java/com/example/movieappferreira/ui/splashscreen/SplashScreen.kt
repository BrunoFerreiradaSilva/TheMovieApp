package com.example.movieappferreira.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappferreira.ui.moviecomplete.MoviePopularCompleteActivity
import com.example.myapplication.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            { goToMoviePopularComplete() },
            SPLASH_DISPLAY_LENGTH.toLong()
        )
    }

    private fun goToMoviePopularComplete() {
        val intent = Intent(this@SplashScreen, MoviePopularCompleteActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val SPLASH_DISPLAY_LENGTH = 1000
    }
}