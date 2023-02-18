package com.lampotrias.seeweather.data.remotelocations.model

import com.lampotrias.seeweather.domain.model.LocationModel

data class LocationEntity(
	val id: Int = 0,
	val name: String,
	val fullName: String = "",
	val latitude: Float,
	val longitude: Float,
) {
	fun toDomainModel(): LocationModel {
		return LocationModel(
			name = name,
			fullName = fullName,
			latitude = latitude,
			longitude = longitude
		)
	}
}