package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = "lbpm29JtUjuAvsbwzY5EPor8nPq0tPtgjYaBD7Ve" //TODO:("Place your Key Here")
    const val  DATABASE_NAME= "asteroid_data_base"
    const val  TABLE_NAME = "asteroid"
    const val WORK_NAME = "refreshWork"

    enum class Filter(){
        ALL_DATA,
        THIS_DAY,
        LAST7DAYS
    }

     fun currDay(): String {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

     fun afterWeek(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,6)
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }
}


