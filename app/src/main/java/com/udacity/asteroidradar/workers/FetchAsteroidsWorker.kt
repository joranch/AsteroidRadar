package com.udacity.asteroidradar.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.data.AsteroidRoomDatabase

private const val TAG = "FetchAsteroidsWorker"
class FetchAsteroidsWorker(private val ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params){
    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Running FetchAsteroidsWorker")
            val database = AsteroidRoomDatabase.getDatabase(ctx).asteroidDao()
            val asteroids = AsteroidApi.getAsteroids()

            database.deleteExpiredAsteroids()
            database.insertAll(asteroids)

            Result.success()

        } catch (e: Throwable) {
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
            Result.retry()
        }
    }
}