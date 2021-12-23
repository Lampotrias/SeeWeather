package com.example.seeweather.data

import com.example.seeweather.data.model.EntityMapper
import com.example.seeweather.data.sources.DataSourceFactory
import com.example.seeweather.domain.WeatherRepo
import com.example.seeweather.domain.model.GeneralWeatherModel
import com.example.seeweather.domain.model.RequestModel
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val factory: DataSourceFactory): WeatherRepo {

	override suspend fun getWeather(requestModel: RequestModel): Result<GeneralWeatherModel> {
		val source = factory.create()
		return source.getDataWeather(requestModel).map { EntityMapper.toDomainModel(requestModel, it) }
	}
}