package com.lampotrias.seeweather.data.weather.model

import android.net.Uri

class HourEntity(
	val date: Long = 0L,
	val tempC: Int = 0,
	val icon: Uri? = null,
	val isDay: Boolean,
	val weatherConditions: WeatherConditions? = null,
	val windPowerKph: Int = 0,
	val windDir: String = "",
	val windDegree: Int
)