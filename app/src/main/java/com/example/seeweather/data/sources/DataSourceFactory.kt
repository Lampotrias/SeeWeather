package com.example.seeweather.data.sources

import android.util.Log
import com.example.seeweather.data.cache.CacheWeather
import com.example.seeweather.data.sources.network.weatherapi.WeatherApiSource
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceFactory @Inject constructor(
	private val okHttpClient: OkHttpClient,
	private val cacheWeather: CacheWeather
) {

	fun create(): DataSourceInterface {
		if (cacheWeather.isExpired()) {
			Log.e("Weather", "cache expired")
		} else {
			Log.e("Weather", "cache NOT expired")
		}

		if (cacheWeather.isExists()) {
			Log.e("Weather", "cache exists")
		} else {
			Log.e("Weather", "cache NOT exists")
		}
		return WeatherApiSource(okHttpClient, cacheWeather)
	}
}