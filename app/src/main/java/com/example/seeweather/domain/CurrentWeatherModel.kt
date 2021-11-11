package com.example.seeweather.domain

class CurrentWeatherModel(
	val lastUpdate: Long = 0L,
	val tempC: Float = 0f,
	val tempF: Float = 0f,
	val isDay: Boolean = true,
	val icon: String = "",
	val windDirection: WindDirection,
	val windMph: Float = 0f,
	val windKph: Float = 0f,
)