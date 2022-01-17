package com.udacity.asteroidradar

import android.app.Application
import com.udacity.asteroidradar.data.AsteroidRoomDatabase

class AsteroidRadarApplication : Application() {
    val database: AsteroidRoomDatabase by lazy { AsteroidRoomDatabase.getDatabase(this) }
}