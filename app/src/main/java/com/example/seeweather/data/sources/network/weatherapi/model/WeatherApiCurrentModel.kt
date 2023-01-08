package com.example.seeweather.data.sources.network.weatherapi.model

import com.example.seeweather.data.model.CurrentWeatherEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WeatherApiCurrentModel {
	@SerialName("last_updated_epoch")
	var lastUpdate: Long = 0L

	@SerialName("temp_c")
	var tempC: Float = 0f

	@SerialName("is_day")
	var isDay: Int = Int.MIN_VALUE

	@SerialName("condition")
	var condition: Condition = Condition()

	@SerialName("wind_dir")
	var windDirection: String = ""

	@SerialName("wind_degree")
	var windDegree: Int = Int.MIN_VALUE

	@SerialName("pressure_mb")
	var pressureMb: Float = 0f

	@SerialName("humidity")
	var humidity: Int = 0

	@SerialName("wind_kph")
	var windKph: Float = 0f

	fun toEntityModel(cityId: Int): CurrentWeatherEntity {
		return CurrentWeatherEntity(
			cityId = cityId,
			date = lastUpdate,
			tempC = tempC,
			icon = "https://${condition.icon}",
			text = condition.text,
			windPowerKph = windKph,
			windDir = windDirection,
			humidity = humidity,
			pressure = pressureMb
		)
	}
}
