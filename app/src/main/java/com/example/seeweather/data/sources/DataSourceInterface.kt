package com.example.seeweather.data.sources

import com.example.seeweather.data.model.GeneralEntityWeatherModel
import com.example.seeweather.domain.model.RequestModel

interface DataSourceInterface {
	suspend fun getDataWeather(requestModel: RequestModel): Result<GeneralEntityWeatherModel>
}