package com.lampotrias.seeweather.domain.model

import android.net.Uri
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.utils.types.Speed
import com.lampotrias.seeweather.utils.types.Temperature
import com.lampotrias.seeweather.utils.types.units.Wind

data class CurrentWeatherModel(
	val temp: Temperature,
	val textStatus: String = "",
	val weatherIcon: Uri? = null,
	val isDay: Boolean,
	val weatherConditions: WeatherConditions? = null,
	val wind: Wind,
	val pressure: Float = 0f,
	val humidity: Int = 0,
	val precipitation: Float = 0f,
	val windGust: Speed,
	val uv: Float = 0f,
	val visibility: Float = 0f,
	val feelsLike: Temperature,
	val cloud: Int = 0,
)
