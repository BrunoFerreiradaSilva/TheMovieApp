package com.example.movieappferreira.ui.moviessimilar

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieappferreira.application.MovieApplication
import com.example.movieappferreira.base.Constants
import com.example.movieappferreira.base.Constants.ID_MOVIE
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.database.MovieRoomViewModel
import com.example.movieappferreira.database.MovieViewModelFactory
import com.example.movieappferreira.extensions.gone
import com.example.movieappferreira.extensions.visible
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MovieSimilar
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.service.ConnectionOn
import com.example.movieappferreira.ui.people.PeopleAdapter
import com.example.movieappferreira.ui.people.PeopleViewModel
import com.example.movieappferreira.ui.peopledetails.PeopleDetailsActivity
import com.example.movieappferreira.utils.MovieClickListener
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMovieSimilarBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieSimilarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieSimilarBinding
    @Inject
    lateinit var peopleViewModel: PeopleViewModel
    @Inject
    lateinit var movieSimilarViewModel: MovieSimilarViewModel
    @Inject lateinit var movieRoomViewModel: MovieRoomViewModel
    private val movieSimilarList = mutableListOf<MovieSimilar>()
    private val peopleList = mutableListOf<People>()
    private lateinit var skeletonScreen: SkeletonScreen
    private val peopleAdapter: PeopleAdapter = PeopleAdapter(this, peopleList, getPeopleDetails())
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
            binding.connectionOff.layoutConnectionOff.visible()
            skeletonScreen.hide()
        }

        binding.connectionOff.buttonRetryConnection.setOnClickListener {
            if (ConnectionOn().isConnected(this)) {
                peopleViewModel.getMovieAndPeopleDetails(movieId)
                movieSimilarViewModel.getMovieSimilar(movieId)
                binding.connectionOff.layoutConnectionOff.gone()
                skeletonScreen.show()
            }
        }
        setupAdapter()
        setupAdapterPeople()


    }

    private fun favoriteMovie(movieDetails: MovieDetails?) {
        binding.layoutItemHeader.favorite.setOnClickListener {
            binding.layoutItemHeader.apply {
                favorite.gone()
                favoriteDone.visible()
                if (movieDetails != null) {
                    movieRoomViewModel.insert(movieDetails)
                }
            }
        }
    }

    private fun removeFavorite(movieDetails: MovieDetails?) {
        binding.layoutItemHeader.favoriteDone.setOnClickListener {
            binding.layoutItemHeader.apply {
                favoriteDone.gone()
                favorite.visible()
                if (movieDetails != null) {
                    movieRoomViewModel.remove(movieDetails.id)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        peopleViewModel.getMovieAndPeopleDetails(movieId)
        movieSimilarViewModel.getMovieSimilar(movieId)
        movieRoomViewModel.allPerson.observe(this) {
            for (item in 0 until it.size) {
                if (movieId == it[item].id) {
                    binding.layoutItemHeader.favoriteDone.visible()
                }
            }
        }
    }

    private fun observeRequest() {
        peopleViewModel.detailsMovieAndPeople.value?.first?.observe(this, {
            setupInformation(it)
        })
        peopleViewModel.detailsMovieAndPeople.value?.second?.observe(this, {
            peopleList.addAll(it)
            peopleAdapter.submitList(peopleList)
            skeletonScreen.hide()
        })
        movieSimilarViewModel.movieSimilarDetails.observe(this, {
            movieSimilarList.addAll(it)
            movieSimilarAdapter.submitList(movieSimilarList)
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

    private fun setupInformation(movieDetails: MovieDetails?) {
        binding.layoutItemHeader.imageHeaderRecyclerSimilar.load(PATH_IMAGE + movieDetails?.backdrop_path)
        binding.layoutItemHeader.nameMovieHeaderRecycler.text = movieDetails?.overview
        setupToolbar(movieDetails)
        favoriteMovie(movieDetails)
        removeFavorite(movieDetails)
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

    private fun setupAdapterPeople() {
        binding.recyclerPeople.apply {
            layoutManager =
                LinearLayoutManager(this@MovieSimilarActivity, RecyclerView.HORIZONTAL, false)
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