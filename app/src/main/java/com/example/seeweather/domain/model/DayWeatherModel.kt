package com.example.seeweather.domain.model

import android.net.Uri
import com.example.seeweather.domain.WindDirection

class DayWeatherModel(
	val date: Long = 0L,
	val icon: Uri? = null,
	val tempMax: Float = 0f,
	val tempMin: Float = 0f,
	val textStatus: String = "",
//	val windDirection: WindDirection,
	val windPower: Float = 0f,
//	val pressure: Float = 0f,
//	val humidity: Int = 0,
	val sunrise: Long = 0L,
	val sunset: Long = 0L,
)