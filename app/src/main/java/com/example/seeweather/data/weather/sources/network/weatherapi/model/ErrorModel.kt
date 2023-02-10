package com.example.seeweather.data.weather.sources.network.weatherapi.model

import kotlinx.serialization.Serializable

@Serializable
class ErrorModel {
	var code: Int = Int.MIN_VALUE
	var message: String = ""
}