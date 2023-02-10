package com.example.seeweather.domain.model

data class GeneralWeatherModel(
	val currentWeatherModel: CurrentWeatherModel,
	val days: List<DayWeatherModel>,
	val hours: List<HourWeatherModel>,
	val actualizeHours: List<HourWeatherModel>
)