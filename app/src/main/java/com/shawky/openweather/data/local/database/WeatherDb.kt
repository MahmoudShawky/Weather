package com.shawky.openweather.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shawky.openweather.data.local.database.dao.WeatherDAO
import com.shawky.openweather.data.local.database.dao.WeatherTable


@Database(entities = [WeatherTable::class], version = 1, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {
    abstract fun wetherDbTableDao(): WeatherDAO
}