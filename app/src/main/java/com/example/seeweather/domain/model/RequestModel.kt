package com.example.seeweather.domain.model

data class RequestModel(
	val city: String,
	val lang: String,
	val tempMetrics: TempMetrics,
	val SpeedMetrics: SpeedMetrics
)

enum class TempMetrics { C, F }
enum class SpeedMetrics { KPH, MPH }