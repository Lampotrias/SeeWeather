package com.example.seeweather.domain.model

import android.net.Uri

class DayWeatherModel(
	val date: Long = 0L,
	val icon: Uri? = null,
	val tempMax: Int = 0,
	val tempMin: Int = 0,
	val textStatus: String = "",
	val windPower: Int = 0,
	val sunrise: Long = 0L,
	val sunset: Long = 0L,
)
