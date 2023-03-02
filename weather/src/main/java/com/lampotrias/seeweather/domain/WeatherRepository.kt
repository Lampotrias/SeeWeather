package com.lampotrias.seeweather.domain

import com.lampotrias.seeweather.domain.model.CurrentShortWeatherModel
import com.lampotrias.seeweather.domain.model.GeneralWeatherModel
import com.lampotrias.seeweather.domain.model.RequestModel

interface WeatherRepository {
	suspend fun getWeather(requestModel: RequestModel): Result<GeneralWeatherModel>
	suspend fun getShortWeatherData(requestModel: RequestModel): Result<CurrentShortWeatherModel>
}
