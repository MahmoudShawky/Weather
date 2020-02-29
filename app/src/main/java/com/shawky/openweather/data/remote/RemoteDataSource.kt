package com.shawky.openweather.data.remote

import com.shawky.openweather.data.remote.models.WeatherModel
import com.shawky.openweather.utils.UseCaseResult

interface RemoteDataSource {
    suspend fun getWeatherData(cityName: String, units: String): UseCaseResult<WeatherModel>
}