package com.example.seeweather.data.model

import android.net.Uri

class DayEntity(
	val date: Long = 0L,
	val tempMaxC: Float = 0f,
	val tempMinC: Float = 0f,
	val textStatus: String = "",
	val icon: Uri? = null,
	val windPowerKph: Float = 0f,
	val sunrise: Long = 0L,
	val sunset: Long = 0L,
)