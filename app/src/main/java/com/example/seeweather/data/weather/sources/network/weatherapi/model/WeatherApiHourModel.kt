package com.example.seeweather.data.weather.sources.network.weatherapi.model

import com.example.seeweather.data.weather.model.HourEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

@Serializable
class WeatherApiHourModel {

	@SerialName("time_epoch")
	var date: Long = 0L

	@SerialName("temp_c")
	var tempC: Float = 0f

	@SerialName("is_day")
	var isDay: Int = Int.MIN_VALUE

	@SerialName("wind_kph")
	var windKph: Float = 0f

	@SerialName("wind_dir")
	var windDirection: String = ""

	@SerialName("wind_degree")
	var windDegree: Int = Int.MIN_VALUE

	@SerialName("pressure_mb")
	var pressureMb: Float = 0f

	@SerialName("humidity")
	var humidity: Int = 0

	@SerialName("condition")
	var condition = Condition()

	fun toEntity(): HourEntity {
		return HourEntity(
			date,
			tempC.roundToInt(),
			condition.imageUri
		)
	}
}
