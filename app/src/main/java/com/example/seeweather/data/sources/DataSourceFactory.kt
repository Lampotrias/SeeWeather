package com.example.seeweather.data.sources

import android.util.Log
import com.example.seeweather.data.cache.CacheWeather
import com.example.seeweather.data.sources.database.dao.ServerSyncStatusDao
import com.example.seeweather.data.sources.network.weatherapi.WeatherApiSource
import com.example.seeweather.domain.model.RequestModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceFactory @Inject constructor(
	private val cacheWeather: CacheWeather,
	private val serverSyncStatusDao: ServerSyncStatusDao
) {
	fun create(requestModel: RequestModel): DataSourceInterface {
		if (cacheWeather.isExpired(requestModel.serverId)) {
			Log.e("Weather", "cache expired")
		} else {
			Log.e("Weather", "cache NOT expired")
		}

		if (cacheWeather.isExists()) {
			Log.e("Weather", "cache exists")
		} else {
			Log.e("Weather", "cache NOT exists")
		}
		return WeatherApiSource(serverSyncStatusDao)
	}
}