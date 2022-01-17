package com.udacity.asteroidradar.api

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

private interface AsteroidApiService {
    // Help source https://stackoverflow.com/questions/58567053/how-to-add-url-parameter-in-a-retrofit-get-request-in-kotlin
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String) : String
}

object AsteroidApi {
    private val retrofitService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }

    suspend fun getAsteroids() : ArrayList<Asteroid> {
        val dateFormatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance(Locale.getDefault())
        val startDate = dateFormatter.format(calendar.time)

        calendar.add(Calendar.DATE, Constants.DEFAULT_END_DATE_DAYS)
        val endDate = dateFormatter.format(calendar.time)

        val stringResult = retrofitService.getAsteroids(startDate, endDate, Constants.API_KEY)

        return parseAsteroidsJsonResult(JSONObject(stringResult))
    }
}