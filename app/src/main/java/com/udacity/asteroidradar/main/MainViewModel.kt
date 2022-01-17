package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidApi
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    fun getAsteroids() {
        viewModelScope.launch {
            try {
                val result = AsteroidApi.getAsteroids()
                val a = ""
            } catch (e: Throwable) {
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }
        }
    }

}