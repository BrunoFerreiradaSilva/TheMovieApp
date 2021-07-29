package com.example.movieappferreira.ui.moviecomplete

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.pm.ActivityInfoCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieappferreira.base.Constants.ID_MOVIE
import com.example.movieappferreira.base.Constants.PAGE
import com.example.movieappferreira.base.Constants.PRIMARY_KEY
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
    private var nextPage: Int = 1
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var skeletonScreen: SkeletonScreen
    private val moviePopularAdapter: MoviePopularCompleteAdapter by lazy {
        MoviePopularCompleteAdapter(this, movieList, getMovieItemClickListener())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviePopularCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSkeleton()

        if (!ConnectionOn().isConnected(this)) {
            binding.connectionOff.layoutConnectionOff.visibility = VISIBLE
            skeletonScreen.hide()
        }

        setupAdapter()

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.apply {
            getPopularMovies(PAGE)
            popularMovieLiveData.observe(this@MoviePopularCompleteActivity, {
                moviePopularAdapter.setData(it)
                skeletonScreen.hide()
            })
        }

        binding.connectionOff.buttonRetryConnection.setOnClickListener {
            if (ConnectionOn().isConnected(this)) {
                movieViewModel.getPopularMovies(PAGE)
                binding.connectionOff.layoutConnectionOff.visibility = GONE
                skeletonScreen.show()
            }

        }
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
                override fun onLoadMore(current_page: Int) {
                    nextPage++
                    movieViewModel.getPopularMovies(nextPage)
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
                val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(applicationContext, R.anim.fade_in, R.anim.move_to_right)
                ActivityCompat.startActivity(this@MoviePopularCompleteActivity, intent, activityOptionsCompat.toBundle())
            }
        }
    }
}