package com.example.seeweather.data.weather.model

class GeneralEntityWeatherModel(
	val currentWeatherModel: CurrentWeatherEntity,
	val days: List<DayEntity>,
	val hours: List<HourEntity>
)
