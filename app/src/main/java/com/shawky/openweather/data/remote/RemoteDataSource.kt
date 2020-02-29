package com.shawky.openweather.data.remote

import com.shawky.openweather.data.remote.models.WeatherModel

interface RemoteDataSource {
    suspend fun getWeatherData(cityName: String, units: String): Result<WeatherModel>
}