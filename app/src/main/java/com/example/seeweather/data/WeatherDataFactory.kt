package com.example.seeweather.data

import com.example.seeweather.data.sources.weatherapi.WeatherApiSource
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataFactory @Inject constructor() {

	@Inject lateinit var okHttpClient: OkHttpClient

	fun create(): WeatherRepoEntity {
		return WeatherApiSource(okHttpClient)
	}
}