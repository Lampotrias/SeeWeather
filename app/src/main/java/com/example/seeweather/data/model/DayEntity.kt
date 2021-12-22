package com.example.seeweather.data.model

import android.net.Uri

class DayEntity(
	val date: Long = 0L,
	val tempMaxC: Float = 0f,
	val tempMinC: Float = 0f,
	val tempMaxF: Float = 0f,
	val tempMinF: Float = 0f,
	val textStatus: String = "",
	val icon: Uri? = null,
//	val windDir: String = "",
	val windPowerMph: Float = 0f,
	val windPowerKph: Float = 0f,
//	val pressure: Float = 0f,
//	val humidity: Int = 0,
	val sunrise: Long = 0L,
	val sunset: Long = 0L,
)