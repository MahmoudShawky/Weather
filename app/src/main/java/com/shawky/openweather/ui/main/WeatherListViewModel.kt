package com.shawky.openweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shawky.openweather.data.Repository
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.data.remote.models.WeatherModel
import com.shawky.openweather.ui.base.BaseViewModel
import com.shawky.openweather.utils.Constants.CURRENT_CITY
import com.shawky.openweather.utils.Constants.TEMPERATURE_UNIT
import com.shawky.openweather.utils.NetworkState
import com.shawky.openweather.utils.UseCaseResult
import kotlinx.coroutines.launch

class WeatherListViewModel(private val repository: Repository) : BaseViewModel() {

    var savedWeathersList = repository.loadAllWeathers()

    private var _weather = MutableLiveData<WeatherModel>()
    val weather: LiveData<WeatherModel>
        get() = _weather

    init {
        getCurrentWeather()
    }

    fun getCurrentWeather() {
        _networkState.postValue(NetworkState.LOADING)
        viewModelScope.launch(handler) {
            when (val result = repository.getWeatherData(CURRENT_CITY, TEMPERATURE_UNIT)) {
                is UseCaseResult.Success -> {
                    _networkState.postValue(NetworkState.LOADED)
                    _weather.postValue(result.data)
                }
                is UseCaseResult.Error -> _networkState.postValue(NetworkState.error(result.exception.message))
            }
        }
    }

    fun saveCurrentWeather() {
        val weatherEntity = _weather.value?.let {
            WeatherEntity(
                dateTime = it.dt,
                cityName = it.name,
                weatherDescription = it.weather[0].description,
                icon = it.weather[0].icon,
                timezone = it.timezone,
                feelsLike = it.main.feelsLike,
                humidity = it.main.humidity,
                pressure = it.main.pressure,
                temp = it.main.temp,
                tempMax = it.main.tempMax,
                tempMin = it.main.tempMin,
                country = it.sys.country,
                sunrise = it.sys.sunrise,
                sunset = it.sys.sunset,
                windSpeed = it.wind.speed
            )
        }

        viewModelScope.launch {
            weatherEntity?.let { repository.insertWeatherModel(it) }
        }

    }
}
