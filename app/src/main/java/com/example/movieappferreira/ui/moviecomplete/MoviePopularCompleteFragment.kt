package com.example.movieappferreira.ui.moviecomplete

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieappferreira.application.MovieApplication
import com.example.movieappferreira.base.Constants
import com.example.movieappferreira.utils.MovieClickListener
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.pagination.EndlessRecyclerOnScrollListener
import com.example.movieappferreira.rest.service.ConnectionOn
import com.example.movieappferreira.ui.moviessimilar.MovieSimilarActivity
import com.example.movieappferreira.ui.home.HomeActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviePopularCompleteFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val movieList: MutableList<MoviePopular> = mutableListOf()
    @Inject
    lateinit var movieViewModel: MovieViewModel
    private val movieRoomViewModel: MovieRoomViewModel by viewModels {
        MovieViewModelFactory((activity?.application as MovieApplication).repository)
    }
    private lateinit var moviePopularAdapter: MoviePopularCompleteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviePopularAdapter =
            MoviePopularCompleteAdapter(requireContext(), movieList, getMovieItemClickListener())
        movieViewModel.getPopularMovies(Constants.PAGE)
        observerRequest()
        if (!ConnectionOn().isConnected(requireContext())) {
            movieRoomViewModel.allPerson.observe(requireActivity(), {
                moviePopularAdapter.setData(it)
            })
            (activity as HomeActivity).hideSkeleton()
        }
        setupAdapter()

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).setToolbar(getString(R.string.title_movie_popular))
    }

    private fun observerRequest() {
        movieViewModel.popularMovieLiveData.observe(requireActivity(), {
            moviePopularAdapter.setData(it)
            movieRoomViewModel.insert(it)
            (activity as HomeActivity).hideSkeleton()
            binding.loadForMoreMovies.visibility = View.GONE

        })
    }

    private fun setupAdapter() {
        binding.recyclerPopularCompleteMovie.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
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
                    Intent(requireContext(), MovieSimilarActivity::class.java)
                intent.putExtra(Constants.ID_MOVIE, id)
                val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                    requireContext(),
                    R.anim.fade_in,
                    R.anim.move_to_right
                )
                ActivityCompat.startActivity(
                    requireContext(),
                    intent,
                    activityOptionsCompat.toBundle()
                )
            }
        }
    }
}