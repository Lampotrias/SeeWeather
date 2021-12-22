package com.example.seeweather.data.model

class GeneralEntityWeatherModel(
	val currentWeatherModel: CurrentWeatherEntity,
	val days: List<DayEntity>,
	val hours: List<HourEntity>
)
