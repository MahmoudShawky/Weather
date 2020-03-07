package com.shawky.openweather.ui.details

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.api.load
import com.shawky.openweather.BuildConfig
import com.shawky.openweather.R
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.ui.base.BaseFragment
import com.shawky.openweather.utils.Constants
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.item_saved_weather.*

class DetailsFragment : BaseFragment<DetailsViewModel>(DetailsViewModel::class) {

    private val safeArgs: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override val resId: Int
        get() = R.layout.details_fragment

    override fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            setWeatherData(it)
        })
    }

    override fun initViews() {
        viewModel.getWeather(safeArgs.dataTime, safeArgs.city)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(parentLayout, "parent_${safeArgs.dataTime}")
            ViewCompat.setTransitionName(tvTemperature, "temp_${safeArgs.dataTime}")
            ViewCompat.setTransitionName(ivIcon, "icon_${safeArgs.dataTime}")
        }
    }

    private fun setWeatherData(weather: WeatherEntity) {
        parentLayout.setCardBackgroundColor(Constants.getColor(weather.dateTime))
        ivIcon.load("${BuildConfig.IMG_BASE_URL}/${weather.icon}@2x.png")

        tvTemperature.text = getString(R.string.temp_celsius, weather.temp)
        tvDate.text = Constants.getDateTimeFormatted(weather.dateTime * 1000)
        tvTempDescription.text = getString(
            R.string.temp_description,
            weather.weatherDescription,
            weather.tempMin,
            weather.tempMax
        )
        tvLocation.text =
            getString(R.string.location, weather.cityName, weather.country)

        tvWind.text = getString(R.string.windValue, weather.windSpeed)
        tvPressure.text = getString(R.string.pressureValue, weather.pressure)
        tvHumidity.text = getString(R.string.humidityValue, weather.humidity)
        tvSunrise.text = Constants.getTimeFormatted(weather.sunrise * 1000)
        tvSunset.text = Constants.getTimeFormatted(weather.sunset * 1000)

    }

}
