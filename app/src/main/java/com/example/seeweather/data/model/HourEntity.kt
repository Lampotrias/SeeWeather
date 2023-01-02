package com.example.seeweather.data.model

import android.net.Uri

class HourEntity(
	val date: Long = 0L,
	val tempC: Float = 0f,
	val icon: Uri? = null,
)