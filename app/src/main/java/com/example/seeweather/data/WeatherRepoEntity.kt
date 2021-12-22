package com.example.seeweather.data

import com.example.seeweather.data.model.GeneralEntityWeatherModel
import com.example.seeweather.domain.model.RequestModel

interface WeatherRepoEntity {
	suspend fun getDataWeather(requestModel: RequestModel): Result<GeneralEntityWeatherModel>
}