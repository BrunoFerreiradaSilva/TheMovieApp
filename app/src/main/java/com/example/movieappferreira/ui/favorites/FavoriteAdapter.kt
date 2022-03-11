package com.example.movieappferreira.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.model.MovieDetails
import com.example.myapplication.databinding.RecyclerItemMoviePopularCompleteBinding

class FavoriteAdapter(
    private val context: Context,
    private val listMovies: MutableList<MovieDetails>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val recyclerItemMoviePopularCompleteBinding: RecyclerItemMoviePopularCompleteBinding =
            RecyclerItemMoviePopularCompleteBinding.inflate(layoutInflater, parent, false)
        return ItemRecycler(recyclerItemMoviePopularCompleteBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val moviePopularList = listMovies?.get(position)
        if (holder is ItemRecycler) {
            holder.apply {
                if (moviePopularList != null) {
                    binding(moviePopularList)
                }
            }
        }
    }

    override fun getItemCount(): Int = listMovies?.size ?: 0

    inner class ItemRecycler(private val recyclerItemPeopleBinding: RecyclerItemMoviePopularCompleteBinding) :
        RecyclerView.ViewHolder(recyclerItemPeopleBinding.root) {
        fun binding(movieDetails: MovieDetails) {
            recyclerItemPeopleBinding.posterPopularCompleteMovie.load(PATH_IMAGE + movieDetails.poster_path)
        }
    }
}