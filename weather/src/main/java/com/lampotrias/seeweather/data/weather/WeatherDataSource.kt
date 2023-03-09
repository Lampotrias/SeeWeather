package com.lampotrias.seeweather.data.weather

import com.lampotrias.seeweather.data.weather.model.CurrentShortWeatherEntity
import com.lampotrias.seeweather.data.weather.model.GeneralWeatherEntity
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.domain.model.RequestModel

interface WeatherDataSource {
	suspend fun getDataWeather(requestModel: RequestModel): Result<GeneralWeatherEntity>
	suspend fun getShortWeatherData(requestModel: RequestModel): Result<CurrentShortWeatherEntity>
	suspend fun getIcon(conditions: Any?): WeatherConditions? = null
}