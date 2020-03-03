package com.shawky.openweather.ui.main

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.shawky.openweather.BuildConfig
import com.shawky.openweather.R
import com.shawky.openweather.ui.base.BaseFragment
import com.shawky.openweather.utils.Status
import kotlinx.android.synthetic.main.fragment_temperature_list.*
import org.koin.android.ext.android.inject

class WeatherListFragment : BaseFragment<WeatherListViewModel>(WeatherListViewModel::class) {

    private val glide: RequestManager by inject()
    private val savedWeatherAdapter: SavedWeathersAdapter by lazy {
        SavedWeathersAdapter()
    }

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

            glide.load("${BuildConfig.IMG_BASE_URL}/${currentWeather.weather[0].icon}@2x.png")
                .into(ivWeatherIcon)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> pbWeatherLoading.visibility = View.VISIBLE
                Status.SUCCESS -> pbWeatherLoading.visibility = View.GONE
                Status.FAILED -> {
                    pbWeatherLoading.visibility = View.GONE
                    it.msg?.let { msg -> showError(msg) }
                }
            }
        })

        viewModel.savedWeathersList.observe(viewLifecycleOwner, Observer {
            if (it != null) savedWeatherAdapter.updateWeathers(it)
        })
    }

    override fun initViews() {
        btnRefresh.setOnClickListener {
            viewModel.getCurrentWeather()
        }

        btnSaveWeather.setOnClickListener {
            viewModel.saveCurrentWeather()
        }

        rvSavedTemp.apply {
            layoutManager = LinearLayoutManager(activity)
            savedWeatherAdapter.itemClickListener = {
                //show details
            }
            adapter = savedWeatherAdapter
        }
    }

}
