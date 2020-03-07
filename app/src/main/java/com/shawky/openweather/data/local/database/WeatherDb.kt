package com.shawky.openweather.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shawky.openweather.data.local.database.dao.WeatherDAO
import com.shawky.openweather.data.local.database.dao.WeatherEntity


@Database(entities = [WeatherEntity::class], version = 3, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDbTableDao(): WeatherDAO
}