package com.shawky.openweather.data.local.database.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WeatherTable")
class WeatherEntity(
    @PrimaryKey
    val dateTime: Long,
    val cityName: String,
    val country: String,
    val weatherDescription: String,
    val icon: String,
    val timezone: Int,
    val feelsLike: Float,
    val humidity: Float,
    val pressure: Float,
    val temp: Float,
    val tempMax: Float,
    val tempMin: Float,
    val sunrise: Int,
    val sunset: Int,
    val windSpeed: Float
)