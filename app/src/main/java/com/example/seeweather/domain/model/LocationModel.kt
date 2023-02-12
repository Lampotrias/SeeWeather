package com.example.seeweather.domain.model

data class LocationModel(
	val name: String,
	val fullName: String = "",
	val latitude: Float,
	val longitude: Float
)
