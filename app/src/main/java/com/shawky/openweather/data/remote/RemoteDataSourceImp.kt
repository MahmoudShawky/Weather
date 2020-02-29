package com.shawky.openweather.data.remote

import com.shawky.openweather.BuildConfig
import com.shawky.openweather.data.remote.models.WeatherModel
import com.shawky.openweather.ui.base.BaseRepository
import com.shawky.openweather.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RemoteDataSourceImp(private val weatherApi: WeatherApi) : RemoteDataSource, BaseRepository() {

    override suspend fun getWeatherData(
        cityName: String,
        units: String
    ): UseCaseResult<WeatherModel> {

        return withContext(Dispatchers.IO) {
            safeApiResult(
                call = {
                    weatherApi.getWeatherData(
                        appId = BuildConfig.API_KEY,
                        cityName = cityName,
                        units = units
                    )
                },
                errorMessage = "Error fetching Weather Data"
            )
        }
    }

}