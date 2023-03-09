package com.lampotrias.seeweather.domain.model

import com.lampotrias.seeweather.utils.Settings

data class RequestModel(
	val city: String,
	val lang: String,
	val tempUnit: Settings.Temp,
	val speedUnit: Settings.Speed
)
