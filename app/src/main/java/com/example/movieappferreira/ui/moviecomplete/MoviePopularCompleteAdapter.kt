package com.example.movieappferreira.ui.moviecomplete

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.model.MoviePopular
import com.example.movieappferreira.utils.BaseItemCallback
import com.example.movieappferreira.utils.MovieClickListener
import com.example.myapplication.databinding.RecyclerItemMoviePopularCompleteBinding

class MoviePopularCompleteAdapter(
    private val context: Context,
    private val moviePopular: MutableList<MoviePopular>,
    private val listener: MovieClickListener
) : ListAdapter<MoviePopular, MoviePopularCompleteAdapter.TypeItem>(BaseItemCallback<MoviePopular>()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePopularCompleteAdapter.TypeItem {
        val layoutInflater = LayoutInflater.from(context)
        val recyclerItemMoviePopularCompleteBinding: RecyclerItemMoviePopularCompleteBinding =
            RecyclerItemMoviePopularCompleteBinding.inflate(layoutInflater, parent, false)
        return TypeItem(recyclerItemMoviePopularCompleteBinding)
    }

    override fun getItemCount(): Int = moviePopular.size

    inner class TypeItem(private val recyclerItemPeopleBinding: RecyclerItemMoviePopularCompleteBinding) :
        RecyclerView.ViewHolder(recyclerItemPeopleBinding.root) {
        fun binding(moviePopular: MoviePopular) {
            recyclerItemPeopleBinding.posterPopularCompleteMovie.load(PATH_IMAGE + moviePopular.poster_path)
        }
    }

    override fun onBindViewHolder(holder: TypeItem, position: Int) {
        val moviePopularList = moviePopular[position]
        holder.apply {
            binding(moviePopularList)
            itemView.setOnClickListener {
                listener.onItemMovieClicked(moviePopularList.id)
            }
        }

    }


}