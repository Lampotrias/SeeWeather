package com.example.seeweather.domain.model

import android.net.Uri

class CurrentWeatherModel(
//	val lastUpdateLocal: Long = 0L,
//	val lastUpdateRemote: Long = 0L,
	val temp: Float = 0f,
	val textStatus: String = "",
	val city: String = "",
	val icon: Uri? = null,
)
