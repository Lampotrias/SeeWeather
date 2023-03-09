package com.lampotrias.seeweather.domain.model

import android.net.Uri
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.domain.WindDirection

data class CurrentWeatherModel(
	val temp: Int = 0,
	val textStatus: String = "",
	val weatherIcon: Uri? = null,
	val isDay: Boolean,
	val weatherConditions: WeatherConditions? = null,
	val windPower: Int = 0,
	val windDirection: WindDirection,
	val pressure: Float = 0f,
	val humidity: Int = 0,
)
