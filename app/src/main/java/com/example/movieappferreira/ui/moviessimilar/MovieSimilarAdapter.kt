package com.example.movieappferreira.ui.moviessimilar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.base.Constants.TYPE_HEADER
import com.example.movieappferreira.base.Constants.TYPE_ITEM
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MovieSimilar
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemHeaderRecyclerSimilarMoviesBinding
import com.example.myapplication.databinding.RecyclerItemMovieSimilarBinding
import com.squareup.picasso.Picasso

class MovieSimilarAdapter(
    private var movieSimilar: MutableList<MovieSimilar> = mutableListOf(),
    private var movieDetails: MovieDetails,
    private val context: Context,
    private val listener: MovieClickListener

) : RecyclerView.Adapter<RecyclerView.ViewHolder>(
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val layoutInflater = LayoutInflater.from(context)
                val itemHeaderList =
                    ItemHeaderRecyclerSimilarMoviesBinding.inflate(layoutInflater, parent, false)
                HeaderItemList(itemHeaderList)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(context)
                val recyclerItemMovieSimilar: RecyclerItemMovieSimilarBinding =
                    RecyclerItemMovieSimilarBinding.inflate(layoutInflater, parent, false)
                ItemMovieSimilar(recyclerItemMovieSimilar)
            }
        }
    }

    inner class HeaderItemList(itemHeaderRecyclerSimilarMoviesBinding: ItemHeaderRecyclerSimilarMoviesBinding) :
        RecyclerView.ViewHolder(itemHeaderRecyclerSimilarMoviesBinding.root) {
        val imageHeader: ImageView = itemView.findViewById(R.id.image_header_recycler_similar)
        val nameMovieHeader: TextView = itemView.findViewById(R.id.name_movie_header_recycler)
    }

    inner class ItemMovieSimilar(recyclerItemMovieSimilarBinding: RecyclerItemMovieSimilarBinding) :
        RecyclerView.ViewHolder(recyclerItemMovieSimilarBinding.root) {
        val imageMovieSimilar: ImageView = itemView.findViewById(R.id.image_item_movie_similar)
        val nameMovieSimilar: TextView = itemView.findViewById(R.id.name_item_movie_similar)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieSimilar = movieSimilar[position]
        if (holder is ItemMovieSimilar) {
            holder.apply {
                Picasso.get().load(PATH_IMAGE + movieSimilar.poster_path).into(imageMovieSimilar)
                nameMovieSimilar.text = movieSimilar.title
                itemView.setOnClickListener {
                    listener.onItemMovieClicked(movieSimilar.id)
                }
            }
        }
        if (holder is HeaderItemList) {
            holder.apply {
                Picasso.get().load(PATH_IMAGE + movieDetails.backdrop_path).into(imageHeader)
                nameMovieHeader.text = movieDetails.original_title
                itemView.setOnClickListener {
                    listener.onItemMovieClicked(movieDetails.id)
                }
            }
        }
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionFooter(position)) TYPE_HEADER else TYPE_ITEM
    }

    fun setData(movieSimilar: MutableList<MovieSimilar>) {
        this.movieSimilar.addAll(movieSimilar)
        notifyDataSetChanged()
    }

    fun setMovie(movieDetails: MovieDetails) {
        this.movieDetails = movieDetails
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieSimilar.size
    }
}