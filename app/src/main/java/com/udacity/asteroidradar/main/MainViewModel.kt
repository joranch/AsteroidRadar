package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.AsteroidDao
import com.udacity.asteroidradar.util.DateHelper
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(private val asteroidDao: AsteroidDao) : ViewModel() {

    val asteroids: LiveData<List<Asteroid>> = asteroidDao
        .getAllFromDate(DateHelper.getTodayAsFormattedString()).asLiveData()

    private var _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay> = _pictureOfTheDay

    fun getAsteroids() {
        viewModelScope.launch {
            try {
                val asteroids = AsteroidApi.getAsteroids()
            } catch (e: Throwable) {
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }
        }
    }

    fun getPictureOfTheDay() {
        viewModelScope.launch {
            try {
                _pictureOfTheDay.value = AsteroidApi.getPictureOfTheDay()
            } catch (e: Throwable) {
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }
        }
    }

}