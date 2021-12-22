package com.example.seeweather.domain.model

class GeneralWeatherModel(
	val currentWeatherModel: CurrentWeatherModel,
	val days: List<DayWeatherModel>,
	val hours: List<HourWeatherModel>
)