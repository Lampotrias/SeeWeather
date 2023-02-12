package com.example.seeweather.data.weather

import com.example.seeweather.data.weather.model.GeneralEntityWeatherModel
import com.example.seeweather.domain.model.RequestModel

interface WeatherDataSource {
	suspend fun getDataWeather(requestModel: RequestModel): Result<GeneralEntityWeatherModel>
}