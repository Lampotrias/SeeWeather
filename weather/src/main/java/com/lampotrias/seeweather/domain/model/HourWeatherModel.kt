package com.lampotrias.seeweather.domain.model

import android.net.Uri
import com.lampotrias.seeweather.domain.WindDirection

class HourWeatherModel(
	val icon: Uri? = null,
	val date: Long = 0,
	val temp: Int = 0,
	val chanceOfRain: Int = 0,
	val windPower: Int = 0,
	val windDirection: WindDirection,
	val windDegree: Int
)