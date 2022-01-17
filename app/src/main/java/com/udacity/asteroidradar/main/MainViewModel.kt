package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.AsteroidDao
import com.udacity.asteroidradar.util.DateHelper
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(private val asteroidDao: AsteroidDao) : ViewModel() {

    val asteroids: LiveData<List<Asteroid>> = asteroidDao
        .getAllFromDate(DateHelper.getTodayAsFormattedString()).asLiveData()


    fun getAsteroids() {
        viewModelScope.launch {
            try {
                val asteroids = AsteroidApi.getAsteroids()
                val pod = AsteroidApi.getPictureOfTheDay()
            } catch (e: Throwable) {
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }
        }
    }

}