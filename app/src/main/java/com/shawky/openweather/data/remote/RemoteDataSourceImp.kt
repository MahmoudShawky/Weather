package com.shawky.openweather.data.remote

import com.shawky.openweather.BuildConfig
import com.shawky.openweather.data.remote.models.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

class RemoteDataSourceImp(val weatherApi: WeatherApi) : RemoteDataSource {

    override suspend fun getWeatherData(cityName: String, units: String): Result<WeatherModel> {
        return withContext(Dispatchers.IO) {
            safeApiResult(
                weatherApi.getWeatherData(
                    appId = BuildConfig.API_KEY,
                    cityName = cityName,
                    units = units
                )
            )
        }
    }

    private fun <T> safeApiResult(call: Response<T>): Result<T> {
        if (call.isSuccessful) return Result.Success(call.body())

        when (call.code()) {
            500 -> throw Failure.ServerException
            401 -> throw Failure.Unauthorized
            else -> return Result.Error.ServerError(call.errorBody())
        }
    }

}

sealed class Result<out T> {
    data class Success<T>(val data: T?) : Result<T>()

    sealed class Error : Result<Nothing>() {
        data class ServerError(val errorBody: ResponseBody?) : Error()
    }
}

sealed class Failure {
    object ServerException : IOException()
    object Unauthorized : IOException()
}