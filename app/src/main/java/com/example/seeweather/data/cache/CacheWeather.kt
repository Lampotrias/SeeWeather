package com.example.seeweather.data.cache

import com.example.seeweather.data.model.ServerUpdateModel

interface CacheWeather {
	fun isExpired(): Boolean
	fun isExists(): Boolean
	fun setLastUpdate(serverUpdateModel: ServerUpdateModel)
}