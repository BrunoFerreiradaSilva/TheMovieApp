package com.example.movieappferreira.ui.moviessimilar

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieappferreira.ui.moviedetails.MovieDetailsViewModel
import com.example.movieappferreira.base.Constants.ID_MOVIE
import com.example.movieappferreira.base.Constants.ID_SIMILAR
import com.example.movieappferreira.base.Constants.PRIMARY_KEY
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.MovieSimilar
import com.example.movieappferreira.rest.service.ConnectionOn
import com.example.movieappferreira.ui.moviedetails.MovieDetailsActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMovieSimilarBinding

class MovieSimilarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieSimilarBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var movieSimilarViewModel: MovieSimilarViewModel
    private val movieSimilarList = mutableListOf<MovieSimilar>()
    private var movieId = 0
    private lateinit var skeletonScreen: SkeletonScreen
    private val movieSimilarAdapter: MovieSimilarAdapter = MovieSimilarAdapter(movieSimilarList,this, onClickItemMovieSimilar())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieSimilarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSkeleton()

        movieId = intent.getIntExtra(ID_MOVIE, 0)

        if (!ConnectionOn().isConnected(this)) {
            binding.connectionOff.layoutConnectionOff.visibility = VISIBLE
            skeletonScreen.hide()
        }

        setupAdapter()

        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        movieDetailsViewModel.getMovieDetails(movieId)
        movieDetailsViewModel.detailsMovieLiveData.observe(this, {
            skeletonScreen.hide()
            if (it != null) {
                movieSimilarAdapter.setMovie(it)
            }
        })

        movieSimilarViewModel = ViewModelProvider(this).get(MovieSimilarViewModel::class.java)
        movieSimilarViewModel.getMovieSimilar(movieId)
        movieSimilarViewModel.movieSimilarDetails.observe(this, {
            movieSimilarAdapter.setData(it)
            skeletonScreen.hide()
        })

        binding.connectionOff.buttonRetryConnection.setOnClickListener {
            if (ConnectionOn().isConnected(this)) {
                movieDetailsViewModel.getMovieDetails(movieId)
                movieSimilarViewModel.getMovieSimilar(movieId)
                binding.connectionOff.layoutConnectionOff.visibility = GONE
                skeletonScreen.show()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.move_to_left, R.anim.fade_out)
    }

    private fun setSkeleton() {
        skeletonScreen = Skeleton.bind(binding.root)
            .load(R.layout.skeleton_header_similar_movies)
            .shimmer(true)
            .duration(2000)
            .show()
    }

    private fun setupAdapter() {
        binding.recyclerSimilarMovies.apply {
            layoutManager = LinearLayoutManager(this@MovieSimilarActivity)
            adapter = movieSimilarAdapter
        }
    }

    private fun onClickItemMovieSimilar(): MovieClickListener {
        return object : MovieClickListener {
            override fun onItemMovieClicked(id: Int) {
                val intent = Intent(this@MovieSimilarActivity, MovieDetailsActivity::class.java)
                intent.putExtra(ID_SIMILAR, id)
                val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(applicationContext, R.anim.fade_in, R.anim.move_to_right)
                ActivityCompat.startActivity(this@MovieSimilarActivity, intent, activityOptionsCompat.toBundle())
            }
        }
    }
}