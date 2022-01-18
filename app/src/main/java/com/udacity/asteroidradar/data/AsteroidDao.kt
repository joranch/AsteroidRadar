package com.udacity.asteroidradar.data

import androidx.room.*
import com.udacity.asteroidradar.util.DateHelper
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Asteroid>)

    @Delete
    suspend fun delete(item: Asteroid)

    @Update
    suspend fun update(item: Asteroid)

    @Query("SELECT * FROM asteroid WHERE id = :id")
    fun getAsteroid(id: Long) : Flow<Asteroid>

    @Query("SELECT * FROM asteroid WHERE close_approach_date >= :date  ORDER by close_approach_date")
    fun getAllFromDate(date: String) : Flow<List<Asteroid>>

    @Query("DELETE FROM asteroid")
    suspend fun clearTable()

    @Query("DELETE FROM asteroid WHERE close_approach_date < :date")
    suspend fun deleteExpiredAsteroids(date: String = DateHelper.getTodayAsFormattedString())
}