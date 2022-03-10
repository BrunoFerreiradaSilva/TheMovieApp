package com.example.movieappferreira.ui.moviessimilar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.utils.MovieClickListener
import com.example.movieappferreira.model.MovieSimilar
import com.example.myapplication.databinding.RecyclerItemMovieSimilarBinding

class MovieSimilarAdapter(
    private var movieSimilar: MutableList<MovieSimilar>,
    private val context: Context,
    private val listener: MovieClickListener

) : RecyclerView.Adapter<RecyclerView.ViewHolder>(
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val recyclerItemMovieSimilar: RecyclerItemMovieSimilarBinding =
            RecyclerItemMovieSimilarBinding.inflate(layoutInflater, parent, false)
        return ItemMovieSimilar(recyclerItemMovieSimilar)
    }


    inner class ItemMovieSimilar(private val recyclerItemMovieSimilarBinding: RecyclerItemMovieSimilarBinding) :
        RecyclerView.ViewHolder(recyclerItemMovieSimilarBinding.root) {
        fun binding(movieSimilar: MovieSimilar) {
            recyclerItemMovieSimilarBinding.imageItemMovieSimilar.load(PATH_IMAGE + movieSimilar.poster_path)
            recyclerItemMovieSimilarBinding.nameItemMovieSimilar.text = movieSimilar.title
        }
    }

    override fun getItemCount(): Int {
        return movieSimilar.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemMovieSimilar) {
            val movieSimilar = movieSimilar[position]
            holder.apply {
                binding(movieSimilar)
                itemView.setOnClickListener {
                    listener.onItemMovieClicked(movieSimilar.id)
                }
            }
        }
    }

    fun setData(movieSimilar: MutableList<MovieSimilar>) {
        this.movieSimilar.addAll(movieSimilar)
        notifyDataSetChanged()
    }

}