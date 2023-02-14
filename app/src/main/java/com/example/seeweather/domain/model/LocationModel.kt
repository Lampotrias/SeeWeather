package com.example.seeweather.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationModel(
	val name: String,
	val fullName: String = "",
	val latitude: Float,
	val longitude: Float
)
