package com.example.movieappferreira.ui.moviecomplete

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.base.Constants.TYPE_FOOTER
import com.example.movieappferreira.base.Constants.TYPE_ITEM
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.MoviePopular
import com.example.myapplication.databinding.LoadForMoreMoviesBinding
import com.example.myapplication.databinding.RecyclerItemMoviePopularCompleteBinding

class MoviePopularCompleteAdapter(
    private val context: Context,
    private val moviePopular: MutableList<MoviePopular>,
    private val listener: MovieClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(context)
                val recyclerItemMoviePopularCompleteBinding: RecyclerItemMoviePopularCompleteBinding =
                    RecyclerItemMoviePopularCompleteBinding.inflate(layoutInflater, parent, false)
                TypeItem(recyclerItemMoviePopularCompleteBinding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(context)
                val loadingForMoreMovies =
                    LoadForMoreMoviesBinding.inflate(layoutInflater, parent, false)
                TypeFooter(loadingForMoreMovies)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val moviePopularList = moviePopular[position]
        if (holder is TypeItem) {
            holder.apply {
                binding(moviePopularList)
                itemView.setOnClickListener {
                    listener.onItemMovieClicked(moviePopularList.id)
                }

            }
        }
        if (holder is TypeFooter) {
            holder.apply {
                binding(this)

            }
        }
    }

    override fun getItemCount(): Int = moviePopular.size

    private fun isPositionFooter(position: Int): Boolean {
        return position == moviePopular.size - 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionFooter(position)) TYPE_FOOTER else TYPE_ITEM
    }

    inner class TypeItem(private val recyclerItemPeopleBinding: RecyclerItemMoviePopularCompleteBinding) :
        RecyclerView.ViewHolder(recyclerItemPeopleBinding.root) {
        fun binding(moviePopular: MoviePopular){
            recyclerItemPeopleBinding.posterPopularCompleteMovie.load(PATH_IMAGE + moviePopular.poster_path)
        }
    }

    inner class TypeFooter(private var loadForMoreMoviesBinding: LoadForMoreMoviesBinding) :
        RecyclerView.ViewHolder(loadForMoreMoviesBinding.root) {
        fun binding(progressBar: TypeFooter){
         progressBar.loadForMoreMoviesBinding.progressBarMovieComplete.isVisible
        }
    }

    fun setData(moviePopularList: MutableList<MoviePopular>) {
        this.moviePopular.addAll(moviePopularList)
        notifyDataSetChanged()
    }
}