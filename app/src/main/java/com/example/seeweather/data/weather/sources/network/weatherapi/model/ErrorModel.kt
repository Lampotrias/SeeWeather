package com.example.seeweather.data.weather.sources.network.weatherapi.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ErrorModel {
	var code: Int = Int.MIN_VALUE
	var message: String = ""
}