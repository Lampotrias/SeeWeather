package com.example.seeweather.data.sources.network.weatherapi.model

import android.util.Log
import com.example.seeweather.data.model.DayEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Serializable
class WeatherApiDayModel {
	@SerialName("date_epoch")
	var date: Long = 0L

	@SerialName("maxtemp_c")
	var maxTempC: Float = 0f

	@SerialName("mintemp_c")
	var minTempC: Float = 0f

	@SerialName("avgtemp_c")
	var avgTempC: Float = 0f

	@SerialName("maxwind_kph")
	var maxWindKph: Float = 0f

	@SerialName("condition")
	var condition: Condition = Condition()

	var sunrise: String = ""
	var sunset: String = ""

	fun toEntity(): DayEntity {
		return DayEntity(
			date,
			maxTempC.roundToInt(),
			minTempC.roundToInt(),
			condition.text,
			condition.imageUri,
			maxWindKph.roundToInt(),
			dateToTimestamp(sunrise),
			dateToTimestamp(sunset)
		)
	}

	private fun dateToTimestamp(date: String): Long {
		val df = SimpleDateFormat(SUN_DATE_FORMAT, Locale.getDefault())
		val sysDate = try {
			df.parse(date)?.time ?: 0L
		} catch (ex: ParseException) {
			Log.e("Weather", "error parse SUN: $date")
			0L
		}
		return sysDate
	}

	companion object {
		private const val SUN_DATE_FORMAT = "hh:mm aa"
	}
}
