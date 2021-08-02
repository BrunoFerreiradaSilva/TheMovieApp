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
import com.example.movieappferreira.base.ImageCacheDownload
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MovieSimilar
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemHeaderRecyclerSimilarMoviesBinding
import com.example.myapplication.databinding.RecyclerItemMovieSimilarBinding
import com.squareup.picasso.Picasso

class MovieSimilarAdapter(
    private var movieSimilar: MutableList<MovieSimilar> = mutableListOf(),
    private val context: Context,
    private val listener: MovieClickListener

) : RecyclerView.Adapter<RecyclerView.ViewHolder>(
) {
    lateinit var movieDetails: MovieDetails
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

    inner class HeaderItemList(private val itemHeaderRecyclerSimilarMoviesBinding: ItemHeaderRecyclerSimilarMoviesBinding) :
        RecyclerView.ViewHolder(itemHeaderRecyclerSimilarMoviesBinding.root) {
        fun binding(movieDetails: MovieDetails){
            ImageCacheDownload.download(itemHeaderRecyclerSimilarMoviesBinding.imageHeaderRecyclerSimilar, movieDetails.backdrop_path)
            itemHeaderRecyclerSimilarMoviesBinding.nameMovieHeaderRecycler.text = movieDetails.original_title
        }
    }

    inner class ItemMovieSimilar(private val recyclerItemMovieSimilarBinding: RecyclerItemMovieSimilarBinding) :
        RecyclerView.ViewHolder(recyclerItemMovieSimilarBinding.root) {
        fun binding(movieSimilar: MovieSimilar){
            ImageCacheDownload.download(recyclerItemMovieSimilarBinding.imageItemMovieSimilar, movieSimilar.poster_path)
            recyclerItemMovieSimilarBinding.nameItemMovieSimilar.text = movieSimilar.title
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieSimilar = movieSimilar[position]
        if (holder is ItemMovieSimilar) {
            holder.apply {
                binding(movieSimilar)
                itemView.setOnClickListener {
                    listener.onItemMovieClicked(movieSimilar.id)
                }
            }
        }
        if (holder is HeaderItemList) {
            holder.apply {
                binding(movieDetails)
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