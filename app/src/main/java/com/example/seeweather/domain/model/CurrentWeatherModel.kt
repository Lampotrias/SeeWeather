package com.example.seeweather.domain.model

import android.net.Uri
import com.example.seeweather.domain.WindDirection

data class CurrentWeatherModel(
//	val lastUpdateLocal: Long = 0L,
//	val lastUpdateRemote: Long = 0L,
	val temp: Int = 0,
	val textStatus: String = "",
	val city: String = "",
	val icon: Uri? = null,
	val windPower: Int = 0,
	val windDirection: WindDirection,
	val pressure: Float = 0f,
	val humidity: Int = 0,
)
