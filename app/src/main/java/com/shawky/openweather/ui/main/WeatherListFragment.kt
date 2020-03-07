package com.shawky.openweather.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.api.load
import com.shawky.openweather.BuildConfig
import com.shawky.openweather.R
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.data.remote.models.WeatherModel
import com.shawky.openweather.ui.base.BaseFragment
import com.shawky.openweather.utils.Constants
import com.shawky.openweather.utils.Status
import kotlinx.android.synthetic.main.fragment_temperature_list.*
import kotlinx.android.synthetic.main.view_current_weather.*

class WeatherListFragment : BaseFragment<WeatherListViewModel>(WeatherListViewModel::class) {

    private val savedWeatherAdapter: SavedWeathersAdapter by lazy {
        SavedWeathersAdapter()
    }

    override val resId: Int
        get() = R.layout.fragment_temperature_list

    override fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            val currentWeather = it ?: return@Observer
            setCurrentWeatherData(currentWeather)
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

    private fun setCurrentWeatherData(currentWeather: WeatherModel) {
        parentLayout.setCardBackgroundColor(Constants.getColor(currentWeather.dt))
        tvTemp.text = getString(R.string.temp_celsius, currentWeather.main.temp)
        tvTempDescription.text = getString(
            R.string.temp_description,
            currentWeather.weather[0].description,
            currentWeather.main.tempMin,
            currentWeather.main.tempMax
        )
        tvLocation.text =
            getString(R.string.location, currentWeather.name, currentWeather.sys.country)

        ivWeatherIcon.load("${BuildConfig.IMG_BASE_URL}/${currentWeather.weather[0].icon}@2x.png")

    }

    override fun initViews() {
        btnRefresh.visibility = View.VISIBLE
        btnRefresh.setOnClickListener {
            viewModel.getCurrentWeather()
        }

        btnSaveWeather.visibility = View.VISIBLE
        btnSaveWeather.setOnClickListener {
            viewModel.saveCurrentWeather()
        }

        rvSavedTemp.apply {

            layoutManager = StaggeredGridLayoutManager(
                3,
                StaggeredGridLayoutManager.VERTICAL
            )//LinearLayoutManager(activity)

            savedWeatherAdapter.itemClickListener =
                object : SavedWeathersAdapter.ItemClickListener {
                    override fun onItemClickListener(
                        item: WeatherEntity,
                        cardView: CardView,
                        imageView: ImageView,
                        tempTextView: TextView
                    ) {
                        val extras = FragmentNavigatorExtras(
                            cardView to "parent_${item.dateTime}",
                            imageView to "icon_${item.dateTime}",
                            tempTextView to "temp_${item.dateTime}"
                        )
                        val action =
                            WeatherListFragmentDirections.actionWeatherListFragmentToDetailsFragment(
                                item.dateTime,
                                item.cityName
                            )
                        findNavController().navigate(action, extras)
                    }
                }

            adapter = savedWeatherAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

}
