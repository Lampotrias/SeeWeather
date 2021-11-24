package com.example.seeweather.data.model

class CurrentWeatherEntity(
	var cityId: Long = 0L,
	var tempC: Float = 0f,
	var tempF: Float = 0f,
	var icon: String = "",
	var text: String = ""
)
