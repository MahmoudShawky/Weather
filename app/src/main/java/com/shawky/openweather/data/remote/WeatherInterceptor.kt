package com.shawky.openweather.data.remote

import android.content.Context
import com.shawky.openweather.R
import com.shawky.openweather.utils.NoInternetException
import com.shawky.openweather.utils.extensions.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Response

class WeatherInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isNetworkConnected()) {
            throw NoInternetException(context.getString(R.string.msg_no_internet))
        }

        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Cache-Control", "max-age=640000")
            .build()

        return chain.proceed(request)
    }
}