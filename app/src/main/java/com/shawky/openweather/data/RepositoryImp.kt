package com.shawky.openweather.data

import androidx.lifecycle.LiveData
import com.shawky.openweather.data.local.database.dao.WeatherDAO
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.data.remote.RemoteDataSource
import com.shawky.openweather.data.remote.models.WeatherModel
import com.shawky.openweather.ui.base.BaseRepository
import com.shawky.openweather.utils.UseCaseResult

class RepositoryImp(private val remoteDataSource: RemoteDataSource, val weatherDAO: WeatherDAO) :
    Repository, BaseRepository() {

    override suspend fun getWeatherData(
        cityName: String,
        units: String
    ): UseCaseResult<WeatherModel> =
        remoteDataSource.getWeatherData(cityName, units)

    override fun insertWeatherModel(weather: WeatherEntity) = weatherDAO.insertWeatherModel(weather)

    override fun deleteWeatherModel(dateTime: Int) = weatherDAO.deleteWeatherModel(dateTime)

    override fun deleteAllWeathers() = weatherDAO.deleteAllWeathers()

    override fun loadAllWeathers(): LiveData<List<WeatherEntity>> = weatherDAO.loadAllWeathers()

}