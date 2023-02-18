package com.lampotrias.seeweather.domain.model

import android.net.Uri

class HourWeatherModel(
	val icon: Uri? = null,
	val date: Long = 0,
	val temp: Int = 0,
	val chanceOfRain: Int = 0
)