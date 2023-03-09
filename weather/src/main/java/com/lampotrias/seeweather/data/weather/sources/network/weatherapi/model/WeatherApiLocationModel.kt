package com.lampotrias.seeweather.data.weather.sources.network.weatherapi.model

import com.lampotrias.seeweather.data.weather.model.WeatherLocationEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class WeatherApiLocationModel(
	@Json(name = "name")
	val name: String,
	@Json(name = "region")
	val region: String,
	@Json(name = "country")
	val country: String,
	@Json(name = "localtime_epoch")
	val localtime: Long,
	@Json(name = "tz_id")
	val tzId: String
) {
	fun toEntityModel(): WeatherLocationEntity {
		return WeatherLocationEntity(
			name = name,
			region = region,
			country = country,
			localtime = localtime,
			timeZone = TimeZone.getTimeZone(tzId)
		)
	}
}