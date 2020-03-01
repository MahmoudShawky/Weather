package com.shawky.openweather.utils

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val CURRENT_CITY = "Cairo"
    const val TEMPERATURE_UNIT =
        "metric"  //Kelvin (Default), Celsius -> metric, Fahrenheit -> imperial

    const val datePattern = "EEE, d MMM yyyy HH:mm:ss"
    val dateFormatter by lazy { SimpleDateFormat(datePattern, Locale.ENGLISH) }

    fun getDateTimeFormatted(timestamp: Long): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        dateFormatter.timeZone = tz
        return dateFormatter.format(Date(timestamp))
    }
}
