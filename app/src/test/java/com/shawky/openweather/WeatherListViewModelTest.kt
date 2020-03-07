package com.shawky.openweather

import androidx.lifecycle.Observer
import com.shawky.openweather.data.Repository
import com.shawky.openweather.data.remote.models.WeatherModel
import com.shawky.openweather.di.*
import com.shawky.openweather.ui.main.WeatherListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherListViewModelTest : KoinTest {
    @Test
    fun `make a test with Koin`() {
        startKoin {
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    databaseModule,
                    apiModule
                )
            )
        }
    }

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private var dataTime: Long? = null
    val viewModel: WeatherListViewModel by inject()
    val repository: Repository by inject()

    @Mock
    lateinit var weather: Observer<WeatherModel>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        //dataTime = repository.loadAllWeathers("Cairo").value?.get(0)?.dateTime
    }

    @Test
    fun testGotCurrentWeather() {
        viewModel.getCurrentWeather()

        viewModel.weather.observeForever(weather)
        //Has received data
        Assert.assertNotNull(viewModel.weather)

        Mockito.verify(weather).onChanged(viewModel.weather.value)

    }
}