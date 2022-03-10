package com.example.movieappferreira.di

import com.example.movieappferreira.ui.home.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Module::class])
interface Component {

    fun inject(homeActivity: HomeActivity)
}