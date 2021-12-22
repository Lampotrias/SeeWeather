package com.example.seeweather.data.model

class CurrentWeatherEntity(
	var cityId: Int = 0,
	var date: Long = 0,
	var tempC: Float = 0f,
	var tempF: Float = 0f,
	var icon: String = "",
	var text: String = ""
)
