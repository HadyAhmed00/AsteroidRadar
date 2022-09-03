package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repo(private val database: AsteroidDatabase) {


    val data: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAll()) {
        it.asAsteroids()
    }

    suspend fun refreshAsteroid(){
        withContext(Dispatchers.IO) {
            val playlist = AsteroidApi.getAsteroids()
            database.asteroidDao.insertAll(playlist.asAsteroidEntities())
        }
    }

}