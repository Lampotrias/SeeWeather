package com.example.seeweather.data

import android.util.Log
import com.example.seeweather.data.cache.CacheWeather
import com.example.seeweather.data.sources.weatherapi.WeatherApiSource
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataFactory @Inject constructor(
	private val okHttpClient: OkHttpClient,
	private val cacheWeather: CacheWeather
) {

	fun create(): WeatherRepoEntity {
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