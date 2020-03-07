package com.shawky.openweather

import androidx.lifecycle.Observer
import com.shawky.openweather.data.Repository
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.di.*
import com.shawky.openweather.ui.details.DetailsViewModel
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

class DetailsViewModelTest : KoinTest {

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

    private var dataTime: Long? = null
    val viewModel: DetailsViewModel by inject()
    val repository: Repository by inject()

    @Mock
    lateinit var weather: Observer<WeatherEntity>


    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        dataTime = repository.loadAllWeathers("Cairo").value?.get(0)?.dateTime
    }

    @Test
    fun testGotWeather() {
        dataTime?.let {
            val selectedWeather = repository.getWeather(it, "Cairo")

            viewModel.weather.observeForever(weather)
            //Has received data
            Assert.assertNotNull(selectedWeather)

            Mockito.verify(weather).onChanged(selectedWeather.value)
        }


    }
}