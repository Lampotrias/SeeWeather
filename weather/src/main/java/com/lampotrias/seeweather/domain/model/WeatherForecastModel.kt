package com.lampotrias.seeweather.domain.model

data class WeatherForecastModel(
	val currentWeatherModel: CurrentWeatherModel,
	val days: List<DayWeatherModel>,
	val hours: List<HourWeatherModel>,
	val actualizeHours: List<HourWeatherModel>
)