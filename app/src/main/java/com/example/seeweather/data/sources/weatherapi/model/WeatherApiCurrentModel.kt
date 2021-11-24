package com.example.seeweather.data.sources.weatherapi.model

import android.net.Uri
import com.example.seeweather.data.model.CurrentWeatherEntity
import com.example.seeweather.domain.model.CurrentWeatherModel
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

	@SerialName("pressure_mb")
	var pressureMb: Float = 0f

	@SerialName("humidity")
	var humidity: Int = 0

	@SerialName("wind_mph")
	var windMph: Float = 0f

	@SerialName("wind_kph")
	var windKph: Float = 0f

	@Serializable
	class Condition {
		var icon: String = ""
		var code: Int = 0
		var text: String = ""
	}

	private val imageUri: Uri
		get() {
			return Uri.parse("https://" + condition.icon.replace("//", ""))
		}

	fun toEntityModel(cityId: Long): CurrentWeatherEntity {
		return CurrentWeatherEntity(
			cityId,
			tempC,
			tempF,
			"https://${condition.icon}",
			condition.text
		)
	}
}