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

    override suspend fun deleteWeatherModel(dateTime: Int) =
        weatherDb.weatherDbTableDao().deleteWeatherModel(dateTime)

    override suspend fun deleteAllWeathers() = weatherDb.weatherDbTableDao().deleteAllWeathers()

    override suspend fun loadAllWeathers(): UseCaseResult<List<WeatherEntity>> {
        val weathers = weatherDb.weatherDbTableDao().loadAllWeathers()
        return UseCaseResult.Success(weathers)
    }

}