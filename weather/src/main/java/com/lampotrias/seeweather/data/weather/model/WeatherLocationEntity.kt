package com.lampotrias.seeweather.data.weather.model

import java.util.*

class WeatherLocationEntity(
	val name: String= "",
	val region: String = "",
	val country: String = "",
	val localtime: Long = 0L,
	val timeZone: TimeZone? = null,
)