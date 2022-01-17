package com.udacity.asteroidradar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Asteroid::class), exportSchema = false, version = 1)
abstract class AsteroidRoomDatabase : RoomDatabase() {

    abstract fun asteroidDao(): AsteroidDao

    // Create singleton
    companion object{
        @Volatile
        private var INSTANCE: AsteroidRoomDatabase? = null

        fun getDatabase(context: Context): AsteroidRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidRoomDatabase::class.java,
                    "asteroid_database")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}