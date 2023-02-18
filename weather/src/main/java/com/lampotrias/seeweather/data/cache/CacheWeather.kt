package com.lampotrias.seeweather.data.cache

interface CacheWeather {
	fun isExpired(serverId: Int): Boolean
	fun isExists(): Boolean
}