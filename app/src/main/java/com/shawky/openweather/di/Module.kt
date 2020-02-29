package com.shawky.openweather.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.shawky.openweather.BuildConfig
import com.shawky.openweather.data.Repository
import com.shawky.openweather.data.RepositoryImp
import com.shawky.openweather.data.local.database.WeatherDb
import com.shawky.openweather.data.local.database.dao.WeatherDAO
import com.shawky.openweather.data.remote.RemoteDataSource
import com.shawky.openweather.data.remote.RemoteDataSourceImp
import com.shawky.openweather.data.remote.WeatherApi
import com.shawky.openweather.data.remote.WeatherInterceptor
import com.shawky.openweather.ui.main.WeatherListViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


val viewModelModule = module {
    viewModel { WeatherListViewModel(repository = get()) }
}

val repositoryModule = module {
    single<RemoteDataSource> { RemoteDataSourceImp(get()) }
    single<Repository> { RepositoryImp(remoteDataSource = get(), weatherDAO = get()) }
}

val appModule = module {
    single { Glide.with(androidContext()) }
}

val apiModule = module {
    fun provideWeatherApiModule(retrofit: Retrofit) = retrofit.create(WeatherApi::class.java)

    single { provideWeatherApiModule(get(named(WEATHER_MODEL))) }
}

val retrofitModule = module {

    fun provideCacheFile(context: Context): File {
        return File(context.cacheDir, "okHttp_cache")
    }

    fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, 1024 * 1024)
    }

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return interceptor
    }

    fun provideInterceptor(context: Context): WeatherInterceptor {
        return WeatherInterceptor(context)
    }

    fun provideHttpClient(
        cache: Cache,
        interceptor: HttpLoggingInterceptor,
        networkInterceptor: WeatherInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor)
            .addInterceptor(networkInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(client)
            .build()
    }

    single { provideCacheFile(androidContext()) }
    single { provideCache(get()) }
    single { provideLoggingInterceptor() }
    single { provideInterceptor(androidContext()) }

    single { provideHttpClient(cache = get(), interceptor = get(), networkInterceptor = get()) }
    single(named(WEATHER_MODEL)) { provideRetrofit(client = get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): WeatherDb {
        return Room.databaseBuilder(application, WeatherDb::class.java, "weather.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(database: WeatherDb): WeatherDAO {
        return database.weatherDbTableDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

private const val WEATHER_MODEL = "weather"
