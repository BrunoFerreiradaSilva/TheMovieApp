package com.example.movieappferreira.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.utils.MovieClickListener
import com.example.myapplication.databinding.RecyclerItemMoviePopularCompleteBinding

class FavoriteAdapter(
    private val context: Context,
    private val listMovies: MutableList<MovieDetails>?,
    private val listener:MovieClickListener
) :
    ListAdapter<MovieDetails,FavoriteAdapter.ItemRecycler>(FavoriteAdapter) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ItemRecycler {
        val layoutInflater = LayoutInflater.from(context)
        val recyclerItemMoviePopularCompleteBinding: RecyclerItemMoviePopularCompleteBinding =
            RecyclerItemMoviePopularCompleteBinding.inflate(layoutInflater, parent, false)
        return ItemRecycler(recyclerItemMoviePopularCompleteBinding)
    }

    override fun getItemCount(): Int = listMovies?.size ?: 0

    inner class ItemRecycler(private val recyclerItemPeopleBinding: RecyclerItemMoviePopularCompleteBinding) :
        RecyclerView.ViewHolder(recyclerItemPeopleBinding.root) {
        fun binding(movieDetails: MovieDetails) {
            recyclerItemPeopleBinding.posterPopularCompleteMovie.load(PATH_IMAGE + movieDetails.poster_path)
        }
    }

    override fun onBindViewHolder(holder: ItemRecycler, position: Int) {
        val moviePopularList = listMovies?.get(position)
        holder.apply {
            if (moviePopularList != null) {
                binding(moviePopularList)
                holder.itemView.setOnClickListener {
                    listener.onItemMovieClicked(moviePopularList.id)
                }
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<MovieDetails>() {
        override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem == newItem
        }
    }
}