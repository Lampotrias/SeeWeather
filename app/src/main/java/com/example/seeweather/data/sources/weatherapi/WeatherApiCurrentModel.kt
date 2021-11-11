package com.example.seeweather.data.sources.weatherapi

import com.example.seeweather.domain.CurrentWeatherModel
import com.example.seeweather.domain.WindDirection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WeatherApiCurrentModel {
	@SerialName("last_updated_epoch")
	var lastUpdate: Long = 0L

	@SerialName("temp_c")
	var tempC: Float = 0f

	@SerialName("temp_f")
	var tempF: Float = 0f

	@SerialName("is_day")
	var isDay: Int = Int.MIN_VALUE

	@SerialName("condition")
	var condition: Condition = Condition()

	@SerialName("wind_dir")
	var windDirection: String = ""

	@SerialName("wind_degree")
	var windDegree: Int = Int.MIN_VALUE

	@SerialName("wind_mph")
	var windMph: Float = 0f

	@SerialName("wind_kph")
	var windKph: Float = 0f

	@Serializable
	class Condition {
		var icon: String = ""
		var code: Int = 0

	}

	fun toDomainModel(): CurrentWeatherModel {
		return CurrentWeatherModel(
			lastUpdate,
			tempC,
			tempF,
			isDay > 0,
			condition.icon,
			WindDirection.valueOfOrDefault(windDirection, WindDirection.W),
			windMph,
			windKph
		)
	}
}