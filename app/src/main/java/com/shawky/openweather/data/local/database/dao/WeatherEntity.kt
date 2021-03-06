package com.shawky.openweather.data.local.database.dao

import androidx.room.Entity

@Entity(tableName = "WeatherTable", primaryKeys = ["dateTime", "cityName"])
class WeatherEntity(
    val dateTime: Long,
    val cityName: String,
    val country: String,
    val weatherDescription: String,
    val icon: String,
    val timezone: Int,
    val feelsLike: Float,
    val humidity: Int,
    val pressure: Int,
    val temp: Float,
    val tempMax: Float,
    val tempMin: Float,
    val sunrise: Long,
    val sunset: Long,
    val windSpeed: Float
)