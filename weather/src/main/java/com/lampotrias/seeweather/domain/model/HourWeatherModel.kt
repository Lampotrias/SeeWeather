package com.lampotrias.seeweather.domain.model

import android.net.Uri
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.utils.types.Temperature
import com.lampotrias.seeweather.utils.types.units.Wind

class HourWeatherModel(
	val icon: Uri? = null,
	val date: Long = 0,
	val temp: Temperature,
	val isDay: Boolean,
	val weatherConditions: WeatherConditions? = null,
	val chanceOfRain: Int = 0,
	val wind: Wind,
)