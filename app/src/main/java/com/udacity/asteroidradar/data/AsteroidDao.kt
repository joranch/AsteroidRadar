package com.udacity.asteroidradar.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Asteroid)

    @Delete
    fun delete(item: Asteroid)

    @Update
    fun update(item: Asteroid)

    @Query("SELECT * FROM asteroid WHERE id = :id")
    fun getAsteroid(id: Long) : Flow<Asteroid>

    @Query("SELECT * FROM asteroid WHERE close_approach_date >= :date  ORDER by close_approach_date")
    fun getAllFromDate(date: String) : Flow<List<Asteroid>>
}