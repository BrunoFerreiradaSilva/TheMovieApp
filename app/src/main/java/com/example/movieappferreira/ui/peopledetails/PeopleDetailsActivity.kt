package com.example.movieappferreira.ui.peopledetails

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieappferreira.base.Constants
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.base.Constants.PRIMARY_KEY
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.service.ConnectionOn
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPeopleDetailsBinding
import com.squareup.picasso.Picasso

class PeopleDetailsActivity : AppCompatActivity() {
    private lateinit var peopleViewModel: PeopleDetailsViewModel
    private var peopleId = 0
    private lateinit var binding: ActivityPeopleDetailsBinding
    private lateinit var skeletonScreen: SkeletonScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSkeleton()

        peopleId = intent.getIntExtra(Constants.PEOPLE_ID, 0)

        if (!ConnectionOn().isConnected(this)) {
            binding.connectionOff.layoutConnectionOff.visibility = VISIBLE
            binding.peopleName.visibility = GONE
            binding.textBirthday.visibility = GONE
            binding.textPlaceOfBirthday.visibility = GONE
            binding.textPopularity.visibility = GONE
            skeletonScreen.hide()
        }

        peopleViewModel = ViewModelProvider(this).get(PeopleDetailsViewModel::class.java)
        peopleViewModel.getMovieDetails(PRIMARY_KEY, peopleId)
        peopleViewModel.detailsPeopleLiveData.observe(this, {
            skeletonScreen.hide()
            setupInformationOnScreen(it)
        })

        binding.connectionOff.buttonRetryConnection.setOnClickListener {
            peopleViewModel.getMovieDetails(PRIMARY_KEY, peopleId)
            binding.connectionOff.layoutConnectionOff.visibility = GONE
            binding.peopleName.visibility = VISIBLE
            binding.textBirthday.visibility = VISIBLE
            binding.textPlaceOfBirthday.visibility = VISIBLE
            binding.textPopularity.visibility = VISIBLE
            skeletonScreen.show()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.move_to_left, R.anim.fade_out)
    }

    private fun setupInformationOnScreen(it: People?) {
        Picasso.get().load(PATH_IMAGE + it?.profile_path).into(binding.peoplePhoto)
        binding.peopleName.text = it?.name
        binding.peopleBiography.text = it?.biography
        binding.peopleBirthday.text = it?.birthday
        binding.peoplePlaceOfBirth.text = it?.place_of_birth
        binding.peoplePopularity.text = it?.popularity
    }

    private fun setSkeleton() {
        skeletonScreen = Skeleton.bind(binding.root)
            .load(R.layout.skeleton_people_details)
            .shimmer(true)
            .duration(2000)
            .show()
    }
}