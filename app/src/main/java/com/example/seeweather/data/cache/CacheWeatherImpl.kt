package com.example.seeweather.data.cache

import android.content.Context
import com.example.seeweather.data.model.ServerUpdateModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CacheWeatherImpl @Inject constructor(@ApplicationContext context: Context) : CacheWeather {

	private val prefs = context.getSharedPreferences("cache", Context.MODE_PRIVATE)

	override fun isExpired(): Boolean {
		val localTime = prefs.getLong(LOCAL_TIME_KEY, 0L)

		return System.currentTimeMillis() / 1000 > localTime + 10 // 10 seconds cache
	}

	override fun isExists(): Boolean {
		return false
	}

	override fun setLastUpdate(serverUpdateModel: ServerUpdateModel) {
		prefs.edit()
			.putLong(LOCAL_TIME_KEY, serverUpdateModel.localLastUpdate)
			.putLong(REMOTE_TIME_KEY, serverUpdateModel.remoteLastUpdate)
			.apply()
	}

	companion object {
		private const val LOCAL_TIME_KEY = "local"
		private const val REMOTE_TIME_KEY = "remote"
	}
}