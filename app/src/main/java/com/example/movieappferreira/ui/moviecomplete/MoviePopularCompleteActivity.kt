package com.example.movieappferreira.ui.moviecomplete

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View.GONE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieappferreira.application.MovieApplication
import com.example.movieappferreira.base.Constants.ID_MOVIE
import com.example.movieappferreira.base.Constants.PAGE
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.pagination.EndlessRecyclerOnScrollListener
import com.example.movieappferreira.rest.service.ConnectionOn
import com.example.movieappferreira.ui.moviessimilar.MovieSimilarActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMoviePopularCompleteBinding

class MoviePopularCompleteActivity : AppCompatActivity() {
    private val movieList: MutableList<MoviePopular> = mutableListOf()
    private lateinit var binding: ActivityMoviePopularCompleteBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var skeletonScreen: SkeletonScreen
    private val movieRoomViewModel: MovieRoomViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository)
    }
    private val moviePopularAdapter: MoviePopularCompleteAdapter =
        MoviePopularCompleteAdapter(this, movieList, getMovieItemClickListener())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviePopularCompleteBinding.inflate(layoutInflater)
        supportActionBar?.title = getString(R.string.title_movie_popular)
        setContentView(binding.root)
        setSkeleton()

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getPopularMovies(PAGE)
        observerRequest()

        if (!ConnectionOn().isConnected(this)) {
            movieRoomViewModel.allPerson.observe(this, {
                moviePopularAdapter.setData(it)
            })
            skeletonScreen.hide()
        }
        setupAdapter()
    }

    private fun observerRequest() {
        movieViewModel.popularMovieLiveData.observe(this@MoviePopularCompleteActivity, {
            moviePopularAdapter.setData(it)
            movieRoomViewModel.insert(it)
            skeletonScreen.hide()
            binding.loadForMoreMovies.visibility = GONE

        })
    }

    private fun setSkeleton() {
        skeletonScreen = Skeleton.bind(binding.root)
            .load(R.layout.skeleton_item_movie_popular)
            .shimmer(true)
            .duration(2000)
            .show()
    }

    private fun setupAdapter() {
        binding.recyclerPopularCompleteMovie.apply {
            layoutManager = GridLayoutManager(this@MoviePopularCompleteActivity, 3)
            adapter = moviePopularAdapter
            addOnScrollListener(object :
                EndlessRecyclerOnScrollListener(layoutManager as GridLayoutManager) {
                override fun onLoadMore(currentPage: Int) {
                    movieViewModel.getPopularMovies(currentPage)
                }
            })
        }
    }

    private fun getMovieItemClickListener(): MovieClickListener {
        return object :
            MovieClickListener {
            override fun onItemMovieClicked(id: Int) {
                val intent =
                    Intent(this@MoviePopularCompleteActivity, MovieSimilarActivity::class.java)
                intent.putExtra(ID_MOVIE, id)
                val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                    applicationContext,
                    R.anim.fade_in,
                    R.anim.move_to_right
                )
                ActivityCompat.startActivity(
                    this@MoviePopularCompleteActivity,
                    intent,
                    activityOptionsCompat.toBundle()
                )
            }
        }
    }
}