package com.lampotrias.seeweather.data.weather

import com.lampotrias.seeweather.data.weather.model.EntityMapper
import com.lampotrias.seeweather.data.weather.sources.WeatherDataSourceFactory
import com.lampotrias.seeweather.domain.WeatherRepository
import com.lampotrias.seeweather.domain.model.GeneralWeatherModel
import com.lampotrias.seeweather.domain.model.RequestModel
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val factory: WeatherDataSourceFactory):
	WeatherRepository {

	override suspend fun getWeather(requestModel: RequestModel): Result<GeneralWeatherModel> {
		val source = factory.create(requestModel)
		return source.getDataWeather(requestModel).map { EntityMapper.toDomainModel(requestModel, it) }
	}
}