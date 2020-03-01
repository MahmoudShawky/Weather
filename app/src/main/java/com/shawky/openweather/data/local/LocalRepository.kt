package com.shawky.openweather.data.local

import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.utils.UseCaseResult

interface LocalRepository {
    suspend fun insertWeatherModel(weatherEntity: WeatherEntity)
    suspend fun deleteWeatherModel(dateTime: Int)
    suspend fun deleteAllWeathers()
    suspend fun loadAllWeathers(): UseCaseResult<List<WeatherEntity>>
}