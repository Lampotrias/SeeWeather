package com.example.seeweather.data.sources.weatherapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WeatherApiDayModel {
	@SerialName("maxtemp_c")
	var maxTempC: Float = 0f

	@SerialName("mintemp_c")
	var minTempC: Float = 0f

	@SerialName("maxtemp_f")
	var maxTempF: Float = 0f

	@SerialName("mintemp_f")
	var minTempF: Float = 0f

	@SerialName("avgtemp_c")
	var avgTempC: Float = 0f

	@SerialName("avgtemp_f")
	var avgTempF: Float = 0f

	@SerialName("maxwind_kph")
	var maxWindKph: Float = 0f

	@Serializable
	class Condition {
		var icon: String = ""
		var code: Int = 0
	}
}