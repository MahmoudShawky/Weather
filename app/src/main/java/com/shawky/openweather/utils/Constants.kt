package com.shawky.openweather.utils

import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val CURRENT_CITY = "Cairo"
    const val TEMPERATURE_UNIT =
        "metric"  //Kelvin (Default), Celsius -> metric, Fahrenheit -> imperial

    const val datePattern = "EEE, d MMM yyyy\nhh:mm a"
    private val dateFormatter by lazy { SimpleDateFormat(datePattern, Locale.ENGLISH) }

    const val timePattern = "hh:mm a"
    private val timeFormatter by lazy { SimpleDateFormat(timePattern, Locale.ENGLISH) }

    fun getDateTimeFormatted(timestamp: Long): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        dateFormatter.timeZone = tz
        return dateFormatter.format(Date(timestamp))
    }

    fun getTimeFormatted(timestamp: Long): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        timeFormatter.timeZone = tz
        return timeFormatter.format(Date(timestamp))
    }


    private fun getDateTime(tz: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(tz * 1000)
        return cal.get(Calendar.DAY_OF_WEEK)
    }

    fun getColor(dt: Long): Int {
        return when (getDateTime(dt)) {
            Calendar.MONDAY -> Color.parseColor("#28E0AE")
            Calendar.TUESDAY -> Color.parseColor("#FF0090")
            Calendar.WEDNESDAY -> Color.parseColor("#FFAE00")
            Calendar.THURSDAY -> Color.parseColor("#0090FF")
            Calendar.FRIDAY -> Color.parseColor("#DC0000")
            Calendar.SATURDAY -> Color.parseColor("#0051FF")
            Calendar.SUNDAY -> Color.parseColor("#3D28E0")
            else -> Color.parseColor("#28E0AE")
        }
    }
}
