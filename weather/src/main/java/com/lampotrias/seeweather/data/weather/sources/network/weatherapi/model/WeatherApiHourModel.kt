package com.lampotrias.seeweather.data.weather.sources.network.weatherapi.model

import com.lampotrias.seeweather.data.weather.model.HourEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.math.roundToInt

@JsonClass(generateAdapter = true)
class WeatherApiHourModel {
	@Json(name = "time_epoch")
	var date: Long = 0L

	@Json(name = "temp_c")
	var tempC: Float = 0f

	@Json(name = "is_day")
	var isDay: Int = Int.MIN_VALUE

	@Json(name = "wind_kph")
	var windKph: Float = 0f

	@Json(name = "wind_dir")
	var windDirection: String = ""

	@Json(name = "wind_degree")
	var windDegree: Int = Int.MIN_VALUE

	@Json(name = "pressure_mb")
	var pressureMb: Float = 0f

	@Json(name = "humidity")
	var humidity: Int = 0

	@Json(name = "condition")
	var condition = Condition()

	fun toEntity(): HourEntity {
		return HourEntity(
			date = date,
			tempC = tempC.roundToInt(),
			icon = condition.imageUri,
			windPowerKph = windKph.toInt(),
			windDir = windDirection,
			windDegree = windDegree
		)
	}
}
