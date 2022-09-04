package com.udacity.asteroidradar.database

import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.afterWeek
import com.udacity.asteroidradar.Constants.currDay
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Repo(private val database: AsteroidDatabase) {


   /* val oneDayAsteroid: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.get7Day(currDay(), currDay())) {
            it.asAsteroids()
        }
    val allAsteroid: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAll()) {
            it.asAsteroids()
        }
    val weekAsteroid: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.get7Day(currDay(), afterWeek())) {
            it.asAsteroids()
        }
*/

    suspend fun refreshAsteroid() {
        withContext(Dispatchers.IO) {
            val playlist = AsteroidApi.getAsteroids("","")
            database.asteroidDao.insertAll(playlist)
        }
    }




}