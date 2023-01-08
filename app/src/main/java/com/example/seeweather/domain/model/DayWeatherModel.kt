package com.example.seeweather.domain.model

import android.net.Uri

class DayWeatherModel(
	val date: Long = 0L,
	val icon: Uri? = null,
	val tempMax: Float = 0f,
	val tempMin: Float = 0f,
	val textStatus: String = "",
	val windPower: Float = 0f,
	val sunrise: Long = 0L,
	val sunset: Long = 0L,
)
