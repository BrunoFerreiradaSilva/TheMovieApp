package com.example.movieappferreira.ui.moviessimilar

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieappferreira.base.Constants
import com.example.movieappferreira.ui.people.PeopleViewModel
import com.example.movieappferreira.base.Constants.ID_MOVIE
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MovieSimilar
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.service.ConnectionOn
import com.example.movieappferreira.ui.people.PeopleAdapter
import com.example.movieappferreira.ui.peopledetails.PeopleDetailsActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMovieSimilarBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieSimilarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieSimilarBinding
    @Inject lateinit var peopleViewModel: PeopleViewModel
    @Inject lateinit var movieSimilarViewModel: MovieSimilarViewModel
    private val movieSimilarList = mutableListOf<MovieSimilar>()
    private val peopleList = mutableListOf<People>()
    private lateinit var skeletonScreen: SkeletonScreen
    private val peopleAdapter: PeopleAdapter = PeopleAdapter(this,peopleList,getPeopleDetails())
    private val movieSimilarAdapter: MovieSimilarAdapter =
        MovieSimilarAdapter(movieSimilarList, this, onClickItemMovieSimilar())
    private var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMovieSimilarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSkeleton()

        movieId = intent.getIntExtra(ID_MOVIE, 0)

        observeRequest()

        if (!ConnectionOn().isConnected(this)) {
            binding.connectionOff.layoutConnectionOff.visibility = VISIBLE
            skeletonScreen.hide()
        }

        binding.connectionOff.buttonRetryConnection.setOnClickListener {
            if (ConnectionOn().isConnected(this)) {
                peopleViewModel.getMovieAndPeopleDetails(movieId)
                movieSimilarViewModel.getMovieSimilar(movieId)
                binding.connectionOff.layoutConnectionOff.visibility = GONE
                skeletonScreen.show()
            }
        }
        setupAdapter()
        setupAdapterPeople()


    }

    override fun onResume() {
        super.onResume()
        peopleViewModel.getMovieAndPeopleDetails(movieId)
        movieSimilarViewModel.getMovieSimilar(movieId)
    }

    private fun observeRequest() {
        peopleViewModel.detailsMovieAndPeople.value?.first?.observe(this, {
            setupInformation(it)
        })
        peopleViewModel.detailsMovieAndPeople.value?.second?.observe(this, {
            peopleAdapter.setData(it)
        })
        movieSimilarViewModel.movieSimilarDetails.observe(this, {
            movieSimilarAdapter.setData(it)
            skeletonScreen.hide()
        })
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

    private fun setupInformation(movieDetails: MovieDetails?){
        binding.layoutItemHeader.imageHeaderRecyclerSimilar.load(PATH_IMAGE + movieDetails?.backdrop_path)
        binding.layoutItemHeader.nameMovieHeaderRecycler.text = movieDetails?.overview
        setupToolbar(movieDetails)

    }

    private fun setupToolbar(movieDetails: MovieDetails?) {
        binding.toolbar.apply {
            title = movieDetails?.original_title
            setTitleTextColor(getColor(R.color.white))
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupAdapter() {
        binding.recyclerSimilarMovies.apply {
            layoutManager = LinearLayoutManager(this@MovieSimilarActivity)
            adapter = movieSimilarAdapter
        }
    }

    private fun setupAdapterPeople(){
        binding.recyclerPeople.apply {
            layoutManager = LinearLayoutManager(this@MovieSimilarActivity,RecyclerView.HORIZONTAL,false)
            adapter = peopleAdapter
        }
    }

    private fun onClickItemMovieSimilar(): MovieClickListener {
        return object : MovieClickListener {
            override fun onItemMovieClicked(id: Int) {
                val intent = Intent(this@MovieSimilarActivity, MovieSimilarActivity::class.java)
                intent.putExtra(ID_MOVIE, id)
                val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                    applicationContext,
                    R.anim.fade_in,
                    R.anim.move_to_right
                )
                ActivityCompat.startActivity(
                    this@MovieSimilarActivity,
                    intent,
                    activityOptionsCompat.toBundle()
                )
            }
        }
    }

    private fun getPeopleDetails(): MovieClickListener {
        return object : MovieClickListener {
            override fun onItemMovieClicked(id: Int) {
                val intent = Intent(this@MovieSimilarActivity, PeopleDetailsActivity::class.java)
                intent.putExtra(Constants.PEOPLE_ID, id)
                val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                    applicationContext,
                    R.anim.fade_in,
                    R.anim.move_to_right
                )
                ActivityCompat.startActivity(
                    this@MovieSimilarActivity,
                    intent,
                    activityOptionsCompat.toBundle()
                )
            }
        }
    }
}