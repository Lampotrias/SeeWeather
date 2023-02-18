package com.lampotrias.seeweather.data.weather.model

import android.net.Uri

class DayEntity(
	val date: Long = 0L,
	val tempMaxC: Int = 0,
	val tempMinC: Int = 0,
	val textStatus: String = "",
	val icon: Uri? = null,
	val windPowerKph: Int = 0,
	val sunrise: Long = 0L,
	val sunset: Long = 0L,
)