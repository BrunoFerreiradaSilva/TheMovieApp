package com.example.movieappferreira.ui.people

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.interfaceclick.MovieClickListener
import com.example.movieappferreira.model.People
import com.example.myapplication.R
import com.example.myapplication.databinding.RecyclerItemPepopleBinding
import java.lang.NullPointerException

class PeopleAdapter(
    private val context: Context,
    private val listPeople: MutableList<People>,
    private val listener: MovieClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val recyclerItemPeopleBinding =
            RecyclerItemPepopleBinding.inflate(layoutInflater, parent, false)
        return ItemPeople(recyclerItemPeopleBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val people = listPeople[position]
        if (holder is ItemPeople) {
            holder.apply {
                try {
                    binding(people)
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

    inner class ItemPeople(private val recyclerItemPeopleBinding: RecyclerItemPepopleBinding
    ) : RecyclerView.ViewHolder(recyclerItemPeopleBinding.root) {
        fun binding(people: People){
            if(people.profile_path != null){
                recyclerItemPeopleBinding.imagePeople.load(PATH_IMAGE + people.profile_path)
            }else{
                recyclerItemPeopleBinding.imagePeople.load(R.drawable.ic_baseline_person_24)
            }
            recyclerItemPeopleBinding.namePeople.text = people.name
        }
    }

}