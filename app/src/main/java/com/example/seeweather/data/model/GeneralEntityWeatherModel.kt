package com.example.seeweather.data.model

import com.example.seeweather.domain.model.HourWeatherModel

class GeneralEntityWeatherModel(
	val currentWeatherModel: CurrentWeatherEntity,
	val days: List<DayEntity>,
	val hours: List<HourEntity>
) {
}