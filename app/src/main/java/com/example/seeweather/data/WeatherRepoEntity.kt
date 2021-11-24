package com.example.seeweather.data

import com.example.seeweather.data.model.CurrentWeatherEntity
import com.example.seeweather.domain.model.CurrentWeatherModel
import com.example.seeweather.domain.model.RequestModel

interface WeatherRepoEntity {
	suspend fun getCurrentWeather(requestModel: RequestModel): Result<CurrentWeatherEntity>
}