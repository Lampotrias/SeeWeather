package com.example.seeweather.data.model

import android.net.Uri

class HourEntity(
	val date: Long = 0L,
	val tempC: Int = 0,
	val icon: Uri? = null,
)