package com.example.seeweather.data.weather

import com.example.seeweather.data.weather.model.EntityMapper
import com.example.seeweather.data.weather.sources.WeatherDataSourceFactory
import com.example.seeweather.domain.WeatherRepository
import com.example.seeweather.domain.model.GeneralWeatherModel
import com.example.seeweather.domain.model.RequestModel
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val factory: WeatherDataSourceFactory): WeatherRepository {

	override suspend fun getWeather(requestModel: RequestModel): Result<GeneralWeatherModel> {
		val source = factory.create(requestModel)
		return source.getDataWeather(requestModel).map { EntityMapper.toDomainModel(requestModel, it) }
	}
}