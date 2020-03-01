package com.shawky.openweather.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shawky.openweather.utils.Failure
import com.shawky.openweather.utils.NetworkState
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    private val _errorObserver = MutableLiveData<Failure>()
    val errorObserver: LiveData<Failure> get() = _errorObserver

    internal val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    val handler = CoroutineExceptionHandler { _, ex ->
        _networkState.postValue(NetworkState.error(ex.message ?: "Unknown Error"))
    }

}