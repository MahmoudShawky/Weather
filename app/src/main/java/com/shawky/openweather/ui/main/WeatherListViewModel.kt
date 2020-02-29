package com.shawky.openweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shawky.openweather.data.Repository
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.data.remote.models.WeatherModel
import com.shawky.openweather.ui.base.BaseViewModel
import com.shawky.openweather.utils.CURRENT_CITY
import com.shawky.openweather.utils.NetworkState
import com.shawky.openweather.utils.TEMPERATURE_UNIT
import com.shawky.openweather.utils.UseCaseResult
import kotlinx.coroutines.launch

class WeatherListViewModel(private val repository: Repository) : BaseViewModel() {

    private var _weather = MutableLiveData<WeatherModel>()
    val weather: LiveData<WeatherModel>
        get() = _weather

    private var _savedWeathersList = MutableLiveData<List<WeatherEntity>>()
    val savedWeathersList: LiveData<List<WeatherEntity>>
        get() = _savedWeathersList

    init {
        getCurrentWeather()
    }

    fun getCurrentWeather() {
        _networkState.postValue(NetworkState.LOADING)
        viewModelScope.launch(handler) {
            val result = repository.getWeatherData(CURRENT_CITY, TEMPERATURE_UNIT)
            when (result) {
                is UseCaseResult.Success -> {
                    _networkState.postValue(NetworkState.LOADED)
                    _weather.postValue(result.data)
                }
                is UseCaseResult.Error -> {
                    _networkState.postValue(NetworkState.error(result.exception.message))
                }
            }
        }
    }
}
