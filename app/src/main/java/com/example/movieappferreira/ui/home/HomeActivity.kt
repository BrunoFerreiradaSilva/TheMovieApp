package com.example.movieappferreira.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieappferreira.application.MovieApplication
import com.example.movieappferreira.ui.moviecomplete.MovieRoomViewModel
import com.example.movieappferreira.ui.moviecomplete.MovieViewModelFactory
import com.example.movieappferreira.utils.Observers
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var skeletonScreen: SkeletonScreen
    private val movieRoomViewModel: MovieRoomViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        showSkeleton()

    }

    fun getAllFavorites(){
        movieRoomViewModel.allPerson.observe(this){

        }
    }

    private fun showSkeleton() {
        skeletonScreen = Skeleton.bind(binding.root)
            .load(R.layout.skeleton_item_movie_popular)
            .shimmer(true)
            .duration(2000)
            .show()
    }
    fun hideSkeleton() {
        skeletonScreen.hide()
    }

    fun setToolbar(titleToolbar:String){
        binding.toolbar.apply {
            title = titleToolbar
            setTitleTextColor(getColor(R.color.white))
        }

    }
}