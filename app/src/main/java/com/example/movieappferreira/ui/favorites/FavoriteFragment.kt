package com.example.movieappferreira.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movieappferreira.ui.home.HomeActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding

class FavoriteFragment : Fragment() {
private lateinit var binding: FragmentDashboardBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentDashboardBinding.inflate(layoutInflater)



    return binding.root
  }

  override fun onResume() {
    super.onResume()
    (activity as HomeActivity).setToolbar(getString(R.string.favorites_title))
  }

}