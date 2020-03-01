package com.shawky.openweather.data

import com.shawky.openweather.data.local.LocalRepository
import com.shawky.openweather.data.remote.RemoteDataSource

interface Repository : RemoteDataSource, LocalRepository
