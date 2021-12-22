package com.example.seeweather.data.sources.weatherapi.model

import android.net.Uri
import com.example.seeweather.data.model.HourEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WeatherApiHourModel {

	@SerialName("time_epoch")
	var date: Long = 0L

	@SerialName("temp_c")
	var tempC: Float = 0f

	@SerialName("temp_f")
	var tempF: Float = 0f

	@SerialName("is_day")
	var isDay: Int = Int.MIN_VALUE

	@SerialName("wind_mph")
	var windMph: Float = 0f

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
			tempC,
			tempF,
			condition.imageUri
		)
	}
}