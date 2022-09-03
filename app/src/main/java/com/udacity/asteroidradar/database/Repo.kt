package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repo(private val database: AsteroidDatabase) {


    fun getData(filter: Constants.Filter):LiveData<List<Asteroid>>{
        val data: LiveData<List<Asteroid>> = Transformations.map(when(filter){
            Constants.Filter.ALL_DATA->database.asteroidDao.getAll()
            Constants.Filter.THIS_DAY->database.asteroidDao.getOneDay("")
            Constants.Filter.LAST7DAYS->database.asteroidDao.get7Day("", "")
            else -> {database.asteroidDao.getAll()}
        }) {
            it.asAsteroids()
        }
        return data
    }

    suspend fun refreshAsteroid(){
        withContext(Dispatchers.IO) {
            val playlist = AsteroidApi.getAsteroids()
            database.asteroidDao.insertAll(playlist.asAsteroidEntities())
        }
    }


}