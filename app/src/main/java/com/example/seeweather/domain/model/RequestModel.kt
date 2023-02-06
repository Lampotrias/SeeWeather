package com.example.seeweather.domain.model

import com.example.seeweather.utils.Settings

data class RequestModel(
	val serverId: Int,
	val city: String,
	val lang: String,
	val tempUnit: Settings.Temp,
	val SpeedUnit: SpeedUnit
)
enum class SpeedUnit { KPH, MPH }