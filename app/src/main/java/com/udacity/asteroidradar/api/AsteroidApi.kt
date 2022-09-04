package com.udacity.asteroidradar.api

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*


object AsteroidApi {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }

    suspend fun getAsteroids(startDate: String,endDate: String): List<Asteroid> {
        val responseStr = retrofitService.getAsteroid(startDate, endDate, API_KEY)
        val responseJsonObject = JSONObject(responseStr)
        val  tmp = parseAsteroidsJsonResult(responseJsonObject)
        return  tmp
    }

    suspend fun getImageOfTheDay(): PictureOfDay {
        return retrofitService.getDayImage(API_KEY)
    }

}

interface AsteroidApiService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroid(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): String

    @GET("planetary/apod")
    suspend fun getDayImage(@Query("api_key") apiKey: String) :PictureOfDay


}


