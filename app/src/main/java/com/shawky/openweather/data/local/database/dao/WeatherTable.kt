package com.shawky.openweather.data.local.database.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WeatherTable")
class WeatherTable(
    @PrimaryKey
    val dateTime: Int,
    val country: String,
    val cityName: String,
    val weatherDescription: String,
    val icon: String,
    val timezone: Int,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Int,
    val tempMax: Int,
    val tempMin: Int,
    val sunrise: Int,
    val sunset: Int,
    val windSpeed: Double
)