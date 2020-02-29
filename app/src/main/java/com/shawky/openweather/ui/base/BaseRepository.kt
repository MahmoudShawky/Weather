package com.shawky.openweather.ui.base

import com.shawky.openweather.utils.Failure
import com.shawky.openweather.utils.UseCaseResult
import retrofit2.Response

open class BaseRepository {
    suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): UseCaseResult<T> {
        val response = call.invoke()
        if (response.isSuccessful) return UseCaseResult.Success(response.body()!!)

        return UseCaseResult.Error(Failure.UnDefined(errorMessage))
    }
}