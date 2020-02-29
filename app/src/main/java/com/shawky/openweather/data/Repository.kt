package com.shawky.openweather.data

import com.shawky.openweather.data.local.database.dao.WeatherDAO
import com.shawky.openweather.data.remote.RemoteDataSource

interface Repository : RemoteDataSource, WeatherDAO {
}