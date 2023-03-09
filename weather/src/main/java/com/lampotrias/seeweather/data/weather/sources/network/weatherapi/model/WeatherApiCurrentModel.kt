package com.lampotrias.seeweather.data.weather.sources.network.weatherapi.model

import com.lampotrias.seeweather.data.weather.model.CurrentWeatherEntity
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.math.roundToInt

@JsonClass(generateAdapter = true)
class WeatherApiCurrentModel {
	@Json(name = "last_updated_epoch")
	var lastUpdate: Long = 0L

	@Json(name = "temp_c")
	var tempC: Float = 0f

	@Json(name = "is_day")
	var isDay: Int = Int.MIN_VALUE

	@Json(name = "condition")
	var condition: Condition = Condition()

	@Json(name = "wind_dir")
	var windDirection: String = ""

	@Json(name = "wind_degree")
	var windDegree: Int = Int.MIN_VALUE

	@Json(name = "pressure_mb")
	var pressureMb: Float = 0f

	@Json(name = "humidity")
	var humidity: Int = 0

	@Json(name = "wind_kph")
	var windKph: Float = 0f

	fun toEntityModel(weatherConditions: WeatherConditions?): CurrentWeatherEntity {
		return CurrentWeatherEntity(
			date = lastUpdate,
			tempC = tempC.roundToInt(),
			iconUri = "https://${condition.icon}",
			weatherConditions = weatherConditions,
			text = condition.text,
			windPowerKph = windKph.roundToInt(),
			windDir = windDirection,
			humidity = humidity,
			isDay = isDay > 0,
			pressure = pressureMb
		)
	}
}
