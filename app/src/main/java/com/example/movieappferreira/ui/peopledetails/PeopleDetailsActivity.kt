package com.example.movieappferreira.ui.peopledetails

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.movieappferreira.base.Constants
import com.example.movieappferreira.base.Constants.PATH_IMAGE
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.service.ConnectionOn
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPeopleDetailsBinding

class PeopleDetailsActivity : AppCompatActivity() {
    private lateinit var peopleViewModel: PeopleDetailsViewModel
    private lateinit var binding: ActivityPeopleDetailsBinding
    private lateinit var skeletonScreen: SkeletonScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSkeleton()

        val peopleId = intent.getIntExtra(Constants.PEOPLE_ID, 0)

        peopleViewModel = ViewModelProvider(this).get(PeopleDetailsViewModel::class.java)
        peopleViewModel.getPeopleDetails(peopleId)
        peopleViewModel.peopleDetailsLiveData.observe(this, {
            skeletonScreen.hide()
            setupInformationOnScreen(it)
        })

        if (!ConnectionOn().isConnected(this)) {
            binding.connectionOff.layoutConnectionOff.visibility = VISIBLE
            binding.peopleName.visibility = GONE
            binding.textBirthday.visibility = GONE
            binding.textPlaceOfBirthday.visibility = GONE
            binding.textPopularity.visibility = GONE
            skeletonScreen.hide()
        }

        binding.connectionOff.buttonRetryConnection.setOnClickListener {
            peopleViewModel.getPeopleDetails(peopleId)
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
        binding.peoplePhoto.load(PATH_IMAGE + it?.profile_path)
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