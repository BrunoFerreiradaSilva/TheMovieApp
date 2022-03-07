package com.example.movieappferreira.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappferreira.ui.home.NewMovieActivity
import com.example.myapplication.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            { goToMoviePopularComplete() },
            SPLASH_DISPLAY_LENGTH.toLong()
        )
    }

    private fun goToMoviePopularComplete() {
        val intent = Intent(this@SplashScreen, NewMovieActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val SPLASH_DISPLAY_LENGTH = 1000
    }
}