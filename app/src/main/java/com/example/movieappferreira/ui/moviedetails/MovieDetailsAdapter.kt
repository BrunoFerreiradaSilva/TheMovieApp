package com.example.movieappferreira.ui.moviedetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.People
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import java.lang.NullPointerException

class MovieDetailsAdapter(
    private val context: Context,
    private val listPeople: MutableList<People>,
    private val listener: MovieClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.recycler_item_pepople, parent, false)
        return ItemPeople(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val people = listPeople[position]
        if (holder is ItemPeople) {
            holder.apply {
                try {
                    Picasso.get().load(PATH_IMAGE + people.profile_path).into(imagePeople)
                    namePeople.text = people.name
                } catch (e: NullPointerException) {
                    return
                }
                itemView.setOnClickListener {
                    listener.onItemMovieClicked(people.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listPeople.size
    }

    fun setData(listPeople: MutableList<People>) {
        this.listPeople.addAll(listPeople)
        notifyDataSetChanged()
    }

    class ItemPeople(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePeople: ImageView = itemView.findViewById(R.id.image_people)
        val namePeople: TextView = itemView.findViewById(R.id.name_people)
    }

}