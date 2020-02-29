package com.shawky.openweather.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherModel(weatherTable: WeatherEntity)

    @Query("DELETE FROM WeatherTable WHERE dateTime = :dateTime ")
    fun deleteWeatherModel(dateTime: Int)

    @Query("DELETE FROM WeatherTable")
    fun deleteAllWeathers()

    @Query("select * from WeatherTable order by dateTime DESC")
    fun loadAllWeathers(): LiveData<List<WeatherEntity>>
}