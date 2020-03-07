package com.shawky.openweather.data

import com.shawky.openweather.data.local.database.WeatherDb
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.data.remote.RemoteDataSource
import com.shawky.openweather.data.remote.models.WeatherModel
import com.shawky.openweather.ui.base.BaseRepository
import com.shawky.openweather.utils.UseCaseResult

class RepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val weatherDb: WeatherDb
) :
    Repository, BaseRepository() {

    override suspend fun getWeatherData(
        cityName: String,
        units: String
    ): UseCaseResult<WeatherModel> =
        remoteDataSource.getWeatherData(cityName, units)

    override suspend fun insertWeatherModel(weatherEntity: WeatherEntity) =
        weatherDb.weatherDbTableDao().insertWeatherModel(weatherEntity)

    override fun deleteWeatherModel(dateTime: Int) =
        weatherDb.weatherDbTableDao().deleteWeatherModel(dateTime)

    override fun deleteAllWeathers() = weatherDb.weatherDbTableDao().deleteAllWeathers()

    override fun loadAllWeathers() = weatherDb.weatherDbTableDao().loadAllWeathers()

    override fun getWeather(dateTime: Long, city: String) =
        weatherDb.weatherDbTableDao().getWeather(dateTime, city)
}