package com.lampotrias.seeweather.data.weather.model

import android.net.Uri

class HourEntity(
	val date: Long = 0L,
	val tempC: Float = 0f,
	val icon: Uri? = null,
	val isDay: Boolean,
	val weatherConditions: WeatherConditions? = null,
	val windPowerKph: Float = 0f,
	val windDir: String = "",
	val windDegree: Int
)