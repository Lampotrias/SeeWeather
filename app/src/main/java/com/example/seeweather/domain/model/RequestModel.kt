package com.example.seeweather.domain.model

data class RequestModel(
	val serverId: Int,
	val city: String,
	val lang: String,
	val tempUnit: TempUnit,
	val SpeedUnit: SpeedUnit
)

enum class TempUnit { C, F }
enum class SpeedUnit { KPH, MPH }