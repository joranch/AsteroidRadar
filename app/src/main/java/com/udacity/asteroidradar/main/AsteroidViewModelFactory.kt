package com.udacity.asteroidradar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.data.AsteroidDao

class AsteroidViewModelFactory(private val asteroidDao: AsteroidDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(asteroidDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}