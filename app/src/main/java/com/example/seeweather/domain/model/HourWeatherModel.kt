package com.example.seeweather.domain.model

import android.net.Uri

class HourWeatherModel(
	val icon: Uri? = null,
	val date: Long = 0,
	val temp: Float = 0f,
	val chanceOfRain: Int = 0
)