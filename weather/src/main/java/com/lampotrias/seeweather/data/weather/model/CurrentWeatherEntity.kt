package com.lampotrias.seeweather.data.weather.model

data class CurrentWeatherEntity(
	val date: Long = 0,
	val tempC: Int = 0,
	val localTime: Long = 0L,
	var iconUri: String = "",
	val isDay: Boolean,
	val weatherConditions: WeatherConditions? = null,
	val text: String = "",
	val windPowerKph: Int = 0,
	val windDir: String = "",
	val pressure: Float = 0f,
	val humidity: Int = 0,
	val precipitation: Float = 0f,
	val windGust: Float = 0f,
	val uv: Float = 0f,
	val visibility: Float = 0f,
	val feelsLike: Float = 0f,
	val cloud: Int = 0,
)
