package com.example.movieappferreira.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieappferreira.application.MovieApplication
import com.example.movieappferreira.extensions.gone
import com.example.movieappferreira.extensions.visible
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.ui.home.HomeActivity
import com.example.movieappferreira.ui.moviecomplete.MovieRoomViewModel
import com.example.movieappferreira.ui.moviecomplete.MovieViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(requireContext(),listMovie)
    }
    private val movieRoomViewModel: MovieRoomViewModel by viewModels {
        MovieViewModelFactory((activity?.application as MovieApplication).repository)
    }
    private val listMovie = mutableListOf<MovieDetails>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        binding.recyclerFavorite.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = favoriteAdapter
        }

        movieRoomViewModel.allPerson.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.layoutNoHaveMovie.visible()
            } else {
                listMovie.addAll(it)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).setToolbar(getString(R.string.favorites_title))

    }

}