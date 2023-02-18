package com.lampotrias.seeweather.data.weather

import com.lampotrias.seeweather.data.weather.model.GeneralEntityWeatherModel
import com.lampotrias.seeweather.domain.model.RequestModel

interface WeatherDataSource {
	suspend fun getDataWeather(requestModel: RequestModel): Result<GeneralEntityWeatherModel>
}