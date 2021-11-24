package com.example.seeweather.data.sources.weatherapi.model

import kotlinx.serialization.Serializable

@Serializable
class ErrorModel {
	var code: Int = Int.MIN_VALUE
	var message: String = ""
}