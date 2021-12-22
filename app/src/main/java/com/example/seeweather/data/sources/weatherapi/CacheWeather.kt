package com.example.seeweather.data.sources.weatherapi

interface CacheWeather {
	fun isExpired(): Boolean
	fun isExists(): Boolean
}