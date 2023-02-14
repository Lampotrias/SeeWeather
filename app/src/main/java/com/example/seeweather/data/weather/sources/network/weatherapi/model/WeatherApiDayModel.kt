package com.example.seeweather.data.weather.sources.network.weatherapi.model

import android.util.Log
import com.example.seeweather.data.weather.model.DayEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt


@JsonClass(generateAdapter = true)
class WeatherApiDayModel {
	@Json(name = "date_epoch")
	var date: Long = 0L

	@Json(name = "maxtemp_c")
	var maxTempC: Float = 0f

	@Json(name = "mintemp_c")
	var minTempC: Float = 0f

	@Json(name = "avgtemp_c")
	var avgTempC: Float = 0f

	@Json(name = "maxwind_kph")
	var maxWindKph: Float = 0f

	@Json(name = "condition")
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
