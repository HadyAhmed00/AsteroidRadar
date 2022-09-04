package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.afterWeek
import com.udacity.asteroidradar.Constants.currDay
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.Repo
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch
import kotlinx.coroutines.plus


class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AsteroidDatabase.getInstance(app)
    private val repository = Repo(database)


    private val _allAsteroids = MutableLiveData<List<Asteroid>>()
        val allAsteroids : LiveData<List<Asteroid>>
        get() = _allAsteroids


    private val _showDetail = MutableLiveData<Asteroid>()
    val showDetail: LiveData<Asteroid>
        get() = _showDetail

    private val _imageOfTheDay = MutableLiveData<PictureOfDay>()
    val imageOfTheDay: LiveData<PictureOfDay>
        get() = _imageOfTheDay

    init {
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

    fun getWeekAsteroids() {
        viewModelScope.launch {
            database.asteroidDao.get7Day(currDay(), afterWeek()).collect {
                _allAsteroids.value=it
            }
        }
    }

    fun getTodayAsteroids() {
        viewModelScope.launch {
            database.asteroidDao.get7Day(currDay(), currDay()).collect {
                _allAsteroids.value = it
            }
        }
    }

    fun getAllAsteroids1() {
        viewModelScope.launch {
            database.asteroidDao.getAll().collect {
                _allAsteroids.value = it
            }
        }
    }
    
    fun displayDetails(asteroid: Asteroid) {
        _showDetail.value = asteroid
    }
    fun finsNav() {
        _showDetail.value = null
    }
}