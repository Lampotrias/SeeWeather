package com.lampotrias.seeweather.domain.model

data class WeatherLocationModel(
	val name: String = "",
	val region: String = "",
	val country: String = "",
	val localtime: Long = 0L
)
