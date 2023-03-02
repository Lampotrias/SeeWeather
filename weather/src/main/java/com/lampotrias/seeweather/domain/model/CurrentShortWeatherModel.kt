package com.lampotrias.seeweather.domain.model

data class CurrentShortWeatherModel(
	val currentWeatherModel: CurrentWeatherModel,
	val weatherLocationModel: WeatherLocationModel
)
