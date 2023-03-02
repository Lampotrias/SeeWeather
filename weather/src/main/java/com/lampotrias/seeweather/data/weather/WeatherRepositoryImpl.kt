package com.lampotrias.seeweather.data.weather

import com.lampotrias.seeweather.data.weather.model.WeatherRepoModelMapper
import com.lampotrias.seeweather.data.weather.sources.WeatherDataSourceFactory
import com.lampotrias.seeweather.domain.WeatherRepository
import com.lampotrias.seeweather.domain.model.CurrentShortWeatherModel
import com.lampotrias.seeweather.domain.model.RequestModel
import com.lampotrias.seeweather.domain.model.WeatherForecastModel
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
	private val factory: WeatherDataSourceFactory
) : WeatherRepository {

	override suspend fun getWeatherForecast(requestModel: RequestModel): Result<WeatherForecastModel> {
		val source = factory.create(requestModel)
		return source.getDataWeather(requestModel).map {
			WeatherRepoModelMapper.toGeneralDomainModel(requestModel, it)
		}
	}

	override suspend fun getShortWeatherData(requestModel: RequestModel): Result<CurrentShortWeatherModel> {
		val source = factory.create(requestModel)
		return source.getShortWeatherData(requestModel).map {
			WeatherRepoModelMapper.toShortDomainModel(requestModel, it)
		}
	}
}
