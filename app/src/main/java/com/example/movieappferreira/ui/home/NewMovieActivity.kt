package com.example.movieappferreira.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNewMovieBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewMovieActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNewMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)


    }

    fun setToolbar(titleToolbar:String){
        binding.toolbar.apply {
            title = titleToolbar
            setTitleTextColor(getColor(R.color.white))
        }

    }
}