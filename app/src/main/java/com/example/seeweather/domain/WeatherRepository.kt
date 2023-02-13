package com.example.seeweather.domain

import com.example.seeweather.domain.model.GeneralWeatherModel
import com.example.seeweather.domain.model.RequestModel

interface WeatherRepository {
	suspend fun getWeather(requestModel: RequestModel): Result<GeneralWeatherModel>
}
