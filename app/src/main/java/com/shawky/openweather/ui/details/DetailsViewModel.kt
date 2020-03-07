package com.shawky.openweather.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.shawky.openweather.data.Repository
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailsViewModel(val repository: Repository) : BaseViewModel() {

    lateinit var weather: LiveData<WeatherEntity>

    fun getWeather(dateTime: Long, city: String) {
        viewModelScope.launch {
            weather = repository.getWeather(dateTime, city)
        }
    }
}
