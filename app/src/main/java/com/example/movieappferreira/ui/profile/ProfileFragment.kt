package com.example.movieappferreira.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movieappferreira.ui.home.NewMovieActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNotificationsBinding

class ProfileFragment : Fragment() {

private lateinit var binding: FragmentNotificationsBinding
  // This property is only valid between onCreateView and
  // onDestroyView.

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentNotificationsBinding.inflate(layoutInflater)


    return binding.root
  }

  override fun onResume() {
    super.onResume()
    (activity as NewMovieActivity).setToolbar(getString(R.string.profile_title))
  }
}