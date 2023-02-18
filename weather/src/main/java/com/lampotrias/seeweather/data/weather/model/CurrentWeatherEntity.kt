package com.lampotrias.seeweather.data.weather.model

data class CurrentWeatherEntity(
	var cityId: Int = 0,
	var date: Long = 0,
	var tempC: Int = 0,
	var icon: String = "",
	var text: String = "",
	val windPowerKph: Int = 0,
	val windDir: String = "",
	val pressure: Float = 0f,
	val humidity: Int = 0,
)
