package com.udacity.asteroidradar.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Repo(private val database: AsteroidDatabase) {


    fun getData(filter: Constants.Filter): LiveData<List<Asteroid>> {
        val data: LiveData<List<Asteroid>> = Transformations.map(when (filter) {
                Constants.Filter.ALL_DATA -> database.asteroidDao.getAll()
                Constants.Filter.THIS_DAY -> database.asteroidDao.getOneDay(currDay())
                Constants.Filter.LAST7DAYS -> database.asteroidDao.get7Day(currDay(), afterWeek())
            }) {
            it.asAsteroids().forEach{
                Log.i("DataFilter","contant${it.closeApproachDate}")
            }
            it.asAsteroids()
        }
        return data
    }

    suspend fun refreshAsteroid() {
        withContext(Dispatchers.IO) {
            val playlist = AsteroidApi.getAsteroids()
            database.asteroidDao.insertAll(playlist.asAsteroidEntities())
        }
    }


    private fun currDay(): String {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

    private fun afterWeek(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,6)
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

}