package com.example.movieappferreira.ui.moviedetails

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieapp.ui.moviedetails.MovieDetailsViewModel
import com.example.movieappferreira.base.Constants.ID_SIMILAR
import com.example.movieappferreira.base.Constants.PEOPLE_ID
import com.example.movieappferreira.base.Constants.PRIMARY_KEY
import com.example.movieappferreira.extensions.loadUrl
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.service.ConnectionOn
import com.example.movieappferreira.ui.peopledetails.PeopleDetailsActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMovieDetailsPopularBinding

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var binding: ActivityMovieDetailsPopularBinding
    private var movieDetails = MovieDetails()
    private val listPeople = mutableListOf<People>()
    private var movieSimilar = 0
    private lateinit var skeletonScreen: SkeletonScreen
    private val movieDetailsAdapter: MovieDetailsAdapter by lazy {
        MovieDetailsAdapter(this, listPeople, getPeopleDetails())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsPopularBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSkeleton()

        if (!ConnectionOn().isConnected(this)) {
            binding.connectionOff.layoutConnectionOff.visibility = VISIBLE
            binding.titleDetailsMoviePopular.visibility = GONE
            skeletonScreen.hide()
        }

        movieSimilar = intent.getIntExtra(ID_SIMILAR, 0)

        setupAdapter()

        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        movieDetailsViewModel.getMovieDetails(apiKey = PRIMARY_KEY, movieID = movieSimilar)
        movieDetailsViewModel.detailsMovieLiveData.observe(this, {
            skeletonScreen.hide()
            if (it != null) {
                movieDetails = it
                setupInformationScreen()
            }
        })

        movieDetailsViewModel.getPeopleMovie(PRIMARY_KEY, movieSimilar)
        movieDetailsViewModel.peopleMovieLiveData.observe(this, {
            skeletonScreen.hide()
            movieDetailsAdapter.setData(it)
        })

        binding.connectionOff.buttonRetryConnection.setOnClickListener {

            if (ConnectionOn().isConnected(this)) {
                movieDetailsViewModel.getMovieDetails(PRIMARY_KEY, movieSimilar)
                movieDetailsViewModel.getPeopleMovie(PRIMARY_KEY, movieSimilar)
                binding.connectionOff.layoutConnectionOff.visibility = GONE
                binding.titleDetailsMoviePopular.visibility = VISIBLE
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
            .load(R.layout.skeleton_movie_details_popular)
            .shimmer(true)
            .duration(2000)
            .show()
    }

    private fun setupAdapter() {
        binding.recyclerPeople.apply {
            layoutManager = LinearLayoutManager(
                this@MovieDetailsActivity, RecyclerView.HORIZONTAL, false
            )
            adapter = movieDetailsAdapter
        }
    }

    private fun setupInformationScreen() {
        binding.apply {
            movieDetails.apply {
                imageDetailsMoviePopular.loadUrl(backdrop_path)
                titleDetailsMoviePopular.text = original_title
                summaryDetails.text = overview
                releaseDateMovieDetails.text = release_date.split("-")[0]
                genreMovieDetails.text =
                    genres.map { it.name }.toString().removePrefix("[").removeSuffix("]")
            }
        }
    }

    private fun getPeopleDetails(): MovieClickListener {
        return object : MovieClickListener {
            override fun onItemMovieClicked(id: Int) {
                val intent = Intent(this@MovieDetailsActivity, PeopleDetailsActivity::class.java)
                intent.putExtra(PEOPLE_ID, id)
                val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                    applicationContext,
                    R.anim.fade_in,
                    R.anim.move_to_right
                )
                ActivityCompat.startActivity(
                    this@MovieDetailsActivity,
                    intent,
                    activityOptionsCompat.toBundle()
                )
            }
        }
    }
}