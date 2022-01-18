package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


// Getting a lot of timeouts. Try changing default timeout. Source used: https://howtodoinjava.com/retrofit2/connection-timeout-exception/
private val httpClient = OkHttpClient.Builder()
    .callTimeout(20, TimeUnit.SECONDS).build()

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder()
    .client(httpClient)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

private interface AsteroidApiService {
    // Help source https://stackoverflow.com/questions/58567053/how-to-add-url-parameter-in-a-retrofit-get-request-in-kotlin
    @GET(NetworkConstants.URI_NEO_FEED)
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String) : String

    @GET(NetworkConstants.URI_POD)
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String) : PictureOfDay
}

object AsteroidApi {
    private val retrofitService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }

    suspend fun getAsteroids() : ArrayList<Asteroid> {
        val dateFormatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance(Locale.getDefault())
        val startDate = dateFormatter.format(calendar.time)

        calendar.add(Calendar.DATE, Constants.DEFAULT_END_DATE_DAYS)
        val endDate = dateFormatter.format(calendar.time)

        val stringResult = retrofitService.getAsteroids(startDate, endDate, NetworkConstants.API_KEY)

        return parseAsteroidsJsonResult(JSONObject(stringResult))
    }

    suspend fun getPictureOfTheDay(): PictureOfDay {
        return retrofitService.getPictureOfTheDay(NetworkConstants.API_KEY)
    }
}