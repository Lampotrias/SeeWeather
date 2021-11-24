package com.example.seeweather.data

import com.example.seeweather.data.model.EntityMapper
import com.example.seeweather.domain.WeatherRepo
import com.example.seeweather.domain.model.CurrentWeatherModel
import com.example.seeweather.domain.model.DayWeatherModel
import com.example.seeweather.domain.model.RequestModel
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(private val factory: WeatherDataFactory): WeatherRepo {

	override suspend fun getCurrentWeather(requestModel: RequestModel): Result<CurrentWeatherModel> {
		val source = factory.create()
		return source.getCurrentWeather(requestModel).map { EntityMapper.toCurrentModel(requestModel, it) }
	}

	override suspend fun getCurrentDayWeather(requestModel: RequestModel): Result<DayWeatherModel> {
		TODO("Not yet implemented")
	}
}