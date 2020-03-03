package com.shawky.openweather.data.local

import androidx.lifecycle.LiveData
import com.shawky.openweather.data.local.database.dao.WeatherEntity

interface LocalRepository {
    suspend fun insertWeatherModel(weatherEntity: WeatherEntity)
    fun deleteWeatherModel(dateTime: Int)
    fun deleteAllWeathers()
    fun loadAllWeathers(): LiveData<List<WeatherEntity>>
}