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

	@Json(name = "precip_mm")
	var precipitation: Float = 0f

	@Json(name = "humidity")
	var humidity: Int = 0

	@Json(name = "wind_kph")
	var windPowerKph: Float = 0f

	@Json(name = "gust_kph")
	var windGustKph: Float = 0f

	@Json(name = "uv")
	var uv: Float = 0f

	@Json(name = "vis_km")
	var visibility: Float = 0f

	@Json(name = "feelslike_c")
	var feelsLike: Float = 0f

	@Json(name = "cloud")
	var cloud: Int = 0

	fun toEntityModel(weatherConditions: WeatherConditions?): CurrentWeatherEntity {
		return CurrentWeatherEntity(
			date = lastUpdate,
			tempC = tempC.roundToInt(),
			iconUri = "https://${condition.icon}",
			weatherConditions = weatherConditions,
			text = condition.text,
			windPowerKph = windPowerKph.roundToInt(),
			windDir = windDirection,
			humidity = humidity,
			isDay = isDay > 0,
			pressure = pressureMb,
			precipitation = precipitation,
			windGust = windGustKph,
			uv = uv,
			visibility = visibility,
			feelsLike = feelsLike,
			cloud = cloud
		)
	}
}
