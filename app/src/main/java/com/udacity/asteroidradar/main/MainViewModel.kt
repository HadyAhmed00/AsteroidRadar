package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.Repo

import kotlinx.coroutines.launch


class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AsteroidDatabase.getInstance(app)
    private val repository = Repo(database)

    lateinit var asteroids: LiveData<List<Asteroid>>

    private val _mutableAsteroids = MutableLiveData<List<Asteroid>>()
    val mutableAsteroids: LiveData<List<Asteroid>>
        get() = _mutableAsteroids

    private val _showDetail = MutableLiveData<Asteroid>()
    val showDetail: LiveData<Asteroid>
        get() = _showDetail

    private val _imageOfTheDay = MutableLiveData<PictureOfDay>()
    val imageOfTheDay: LiveData<PictureOfDay>
        get() = _imageOfTheDay

    init {
        fillData(Constants.Filter.THIS_DAY)
        getMarsRealEstateProperties()
        getImageOfTheDay()

    }

    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                repository.refreshAsteroid()

            } catch (e: Exception) {

                Log.i("getData", "the Error is ${e.message}")
            }
        }
    }

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            try {
                _imageOfTheDay.value = AsteroidApi.getImageOfTheDay()
            } catch (e: Exception) {
                Log.i("ImageOfTheDay", "the Error Massage is ${e.message}")
            }
        }


    }

    fun fillData(filter: Constants.Filter) {
        asteroids = repository.getData(filter)
        _mutableAsteroids.value = asteroids.value
    }

    fun displayDetails(asteroid: Asteroid) {
        _showDetail.value = asteroid
    }

    fun finsNav() {
        _showDetail.value = null
    }
}