package com.lampotrias.seeweather.domain.model

import android.net.Uri
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.utils.types.Temperature

class DayWeatherModel(
	val date: Long = 0L,
	val icon: Uri? = null,
	val tempMax: Temperature,
	val tempMin: Temperature,
	val textStatus: String = "",
	val windPower: Float = 0f,
	val sunrise: Long = 0L,
	val sunset: Long = 0L,
	val weatherConditions: WeatherConditions? = null
)
