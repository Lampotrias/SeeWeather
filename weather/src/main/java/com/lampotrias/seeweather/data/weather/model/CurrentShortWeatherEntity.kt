package com.lampotrias.seeweather.data.weather.model

data class CurrentShortWeatherEntity(
	val currentWeatherEntity: CurrentWeatherEntity,
	val weatherLocationEntity: WeatherLocationEntity
)