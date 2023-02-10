package com.example.seeweather.data.cache

import com.example.seeweather.data.weather.sources.database.dao.ServerSyncStatusDao
import javax.inject.Inject

class CacheWeatherImpl @Inject constructor(
	private val serverSyncStatusDao: ServerSyncStatusDao
) : CacheWeather {

	override fun isExpired(serverId: Int): Boolean {
		val status = serverSyncStatusDao.getStatus(serverId) ?: return true

		val localTime = status.localLastUpdate
		return System.currentTimeMillis() / 1000 > localTime + 10 // 10 seconds cache
	}

	override fun isExists(): Boolean {
		return false
	}
}