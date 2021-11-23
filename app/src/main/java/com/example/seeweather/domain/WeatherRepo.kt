package com.example.seeweather.domain

import com.example.seeweather.domain.model.CurrentWeatherModel
import com.example.seeweather.domain.model.DayWeatherModel
import com.example.seeweather.domain.model.RequestModel

interface WeatherRepo {
	suspend fun getCurrentWeather(requestModel: RequestModel): Result<CurrentWeatherModel>
	suspend fun getCurrentDayWeather(requestModel: RequestModel): Result<DayWeatherModel>

//	suspend fun getDailyWeather(requestModel: RequestModel): Result<DailyWeatherModel>
}