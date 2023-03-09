package com.lampotrias.seeweather.data.weather.sources

import android.util.Log
import com.lampotrias.seeweather.data.cache.CacheWeather
import com.lampotrias.seeweather.data.weather.WeatherDataSource
import com.lampotrias.seeweather.data.weather.sources.database.dao.ServerSyncStatusDao
import com.lampotrias.seeweather.data.weather.sources.network.weatherapi.WeatherApiSourceWeather
import com.lampotrias.seeweather.domain.model.RequestModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataSourceFactory @Inject constructor(
	private val cacheWeather: CacheWeather,
	private val serverSyncStatusDao: ServerSyncStatusDao,
) {
	fun create(requestModel: RequestModel): WeatherDataSource {
//		if (cacheWeather.isExpired(requestModel.serverId)) {
//			Log.e("Weather", "cache expired")
//		} else {
//			Log.e("Weather", "cache NOT expired")
//		}

		if (cacheWeather.isExists()) {
			Log.e("Weather", "cache exists")
		} else {
			Log.e("Weather", "cache NOT exists")
		}
		return WeatherApiSourceWeather(serverSyncStatusDao)
	}
}