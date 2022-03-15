package com.example.movieappferreira.ui.favorites

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
import com.example.movieappferreira.database.MovieRoomViewModel
import com.example.movieappferreira.database.MovieViewModelFactory
import com.example.movieappferreira.extensions.visible
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.ui.home.HomeActivity
import com.example.movieappferreira.ui.moviessimilar.MovieSimilarActivity
import com.example.movieappferreira.utils.MovieClickListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(requireContext(), listMovie, getMovieItemClickListener())
    }
    @Inject lateinit var movieRoomViewModel: MovieRoomViewModel
    private val listMovie = mutableListOf<MovieDetails>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).setToolbar(getString(R.string.favorites_title))
        getAllFavorites()
        binding.recyclerFavorite.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = favoriteAdapter
        }

    }

    private fun getAllFavorites() {
        movieRoomViewModel.allPerson.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.layoutNoHaveMovie.visible()
            } else {
                listMovie.clear()
                listMovie.addAll(it)
                favoriteAdapter.submitList(it)
            }
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