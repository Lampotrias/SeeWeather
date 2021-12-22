package com.example.seeweather.domain

import com.example.seeweather.domain.model.CurrentWeatherModel
import com.example.seeweather.domain.model.DayWeatherModel
import com.example.seeweather.domain.model.GeneralWeatherModel
import com.example.seeweather.domain.model.RequestModel

interface WeatherRepo {
	suspend fun getWeather(requestModel: RequestModel): Result<GeneralWeatherModel>
}