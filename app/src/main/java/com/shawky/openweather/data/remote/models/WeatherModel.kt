package com.shawky.openweather.data.remote.models


import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) {
    data class Clouds(
        @SerializedName("all")
        val all: Int
    )

    data class Coord(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double
    )

    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Float,
        @SerializedName("humidity")
        val humidity: Float,
        @SerializedName("pressure")
        val pressure: Float,
        @SerializedName("temp")
        val temp: Float,
        @SerializedName("temp_max")
        val tempMax: Float,
        @SerializedName("temp_min")
        val tempMin: Float
    )

    data class Sys(
        @SerializedName("country")
        val country: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("sunrise")
        val sunrise: Int,
        @SerializedName("sunset")
        val sunset: Int,
        @SerializedName("type")
        val type: Int
    )

    data class Weather(
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String
    )

    data class Wind(
        @SerializedName("deg")
        val deg: Float,
        @SerializedName("speed")
        val speed: Float
    )
}