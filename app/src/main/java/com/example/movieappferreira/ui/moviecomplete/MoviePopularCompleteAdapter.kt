package com.example.movieappferreira.ui.moviecomplete

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieappferreira.base.ImageDownload
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.MoviePopular
import com.example.myapplication.databinding.RecyclerItemMoviePopularCompleteBinding

class MoviePopularCompleteAdapter(
    private val context: Context,
    private val moviePopular: MutableList<MoviePopular>,
    private val listener: MovieClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val recyclerItemMoviePopularCompleteBinding: RecyclerItemMoviePopularCompleteBinding =
            RecyclerItemMoviePopularCompleteBinding.inflate(layoutInflater, parent, false)
        return TypeItem(recyclerItemMoviePopularCompleteBinding)
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
    }

    override fun getItemCount(): Int = moviePopular.size

    inner class TypeItem(private val recyclerItemPeopleBinding: RecyclerItemMoviePopularCompleteBinding) :
        RecyclerView.ViewHolder(recyclerItemPeopleBinding.root) {
        fun binding(moviePopular: MoviePopular) {
            val request = ImageDownload.download(
                context,
                moviePopular,
                recyclerItemPeopleBinding.posterPopularCompleteMovie
            )
            val fileName = request.data.toString()
            recyclerItemPeopleBinding.posterPopularCompleteMovie.load(fileName)
        }
    }

    fun setData(moviePopularList: MutableList<MoviePopular>) {
        this.moviePopular.addAll(moviePopularList)
        notifyDataSetChanged()
    }

}