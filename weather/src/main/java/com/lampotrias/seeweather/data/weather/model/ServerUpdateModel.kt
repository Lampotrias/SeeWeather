package com.lampotrias.seeweather.data.weather.model

data class ServerUpdateModel(
	val localLastUpdate: Long = 0,
	val remoteLastUpdate: Long = 0
)
