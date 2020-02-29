package com.shawky.openweather.data.remote

import com.shawky.openweather.data.remote.models.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherData(
        @Query("appid") appId: String,
        @Query("q") cityName: String,
        @Query("units") units: String
    ): Response<WeatherModel>

}