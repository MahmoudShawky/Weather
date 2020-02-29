package com.shawky.openweather.data

import androidx.lifecycle.LiveData
import com.shawky.openweather.data.local.database.dao.WeatherDAO
import com.shawky.openweather.data.local.database.dao.WeatherTable
import com.shawky.openweather.data.remote.RemoteDataSource
import com.shawky.openweather.data.remote.Result
import com.shawky.openweather.data.remote.models.WeatherModel

class RepositoryImp(private val remoteDataSource: RemoteDataSource, val weatherDAO: WeatherDAO) :
    Repository {

    override suspend fun getWeatherData(cityName: String, units: String): Result<WeatherModel> =
        remoteDataSource.getWeatherData(cityName, units)

    override fun insertWeatherModel(weather: WeatherTable) = weatherDAO.insertWeatherModel(weather)

    override fun deleteWeatherModel(dateTime: Int) = weatherDAO.deleteWeatherModel(dateTime)

    override fun deleteAllWeathers() = weatherDAO.deleteAllWeathers()

    override fun loadAllWeathers(): LiveData<List<WeatherTable>> = weatherDAO.loadAllWeathers()

}