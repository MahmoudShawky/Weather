package com.shawky.openweather.ui.main

import androidx.lifecycle.Observer
import com.shawky.openweather.R
import com.shawky.openweather.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_temperature_list.*

class WeatherListFragment : BaseFragment<WeatherListViewModel>(WeatherListViewModel::class) {

    override val resId: Int
        get() = R.layout.fragment_temperature_list

    override fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            val currentWeather = it ?: return@Observer
            tvTemp.text = getString(R.string.temp_celsius, currentWeather.main.temp)
            tvTempDescription.text = getString(
                R.string.temp_description,
                currentWeather.weather[0].description,
                currentWeather.main.tempMin,
                currentWeather.main.tempMax
            )

            tvLocation.text = currentWeather.name
        })
    }

    override fun initViews() {

    }

}
