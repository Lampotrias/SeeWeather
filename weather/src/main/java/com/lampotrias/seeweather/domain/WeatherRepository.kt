package com.lampotrias.seeweather.domain

import com.lampotrias.seeweather.domain.model.CurrentShortWeatherModel
import com.lampotrias.seeweather.domain.model.RequestModel
import com.lampotrias.seeweather.domain.model.WeatherForecastModel

interface WeatherRepository {
	suspend fun getWeatherForecast(requestModel: RequestModel): Result<WeatherForecastModel>
	suspend fun getShortWeatherData(requestModel: RequestModel): Result<CurrentShortWeatherModel>
}
