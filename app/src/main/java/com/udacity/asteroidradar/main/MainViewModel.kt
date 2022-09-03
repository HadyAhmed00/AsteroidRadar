package com.udacity.asteroidradar.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {


    private val _showDetail= MutableLiveData<Asteroid>()
    val showDetail : LiveData<Asteroid>
    get() = _showDetail


    private val _listData = MutableLiveData<List<Asteroid>>()
    val listData : LiveData<List<Asteroid>>
    get()=_listData

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
    get() = _response


    private val _imageOfTheDay = MutableLiveData<PictureOfDay>()
        val imageOfTheDay : LiveData<PictureOfDay>
        get() = _imageOfTheDay

    init {
        _listData.value = ArrayList()
        getMarsRealEstateProperties()
        getImageOfTheDay()

    }
    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                _listData.value = AsteroidApi.getAsteroids()
                Log.i("MainViewModel","the data in the 1st element is ${_listData.value}")

            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
                Log.i("getData","the Error is ${e.message}")
            }
        }
    }

    private fun getImageOfTheDay(){
        viewModelScope.launch {
            try {
                Log.i("ImageOfTheDay","Done")
                _imageOfTheDay.value=AsteroidApi.getImageOfTheDay()
                Log.i("ImageOfTheDay","Done")
            }catch (e:Exception) {
                Log.i("ImageOfTheDay","the Error Massage is ${e.message}")
            }
        }


    }

    fun displayDetails(asteroid: Asteroid){
        _showDetail.value=asteroid
    }
    fun finsNav(){
        _showDetail.value = null
    }
}