package com.lampotrias.seeweather.domain.model

import java.util.*

data class WeatherLocationModel(
	val name: String = "",
	val region: String = "",
	val country: String = "",
	val localtime: Long = 0L,
	val timeZone: TimeZone? = null,
)
