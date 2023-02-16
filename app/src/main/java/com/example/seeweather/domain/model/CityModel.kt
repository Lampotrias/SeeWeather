package com.example.seeweather.domain.model

import com.example.seeweather.data.city.model.CityEntity

data class CityModel(
	val id: Int = 0,
	val name: String,
	val latitude: Float,
	val longitude: Float,
	val sort: Int = 500,
	val isLast: Boolean = false
) {
	fun toEntity(): CityEntity {
		return CityEntity(
			id,
			sort,
			name,
			latitude,
			longitude,
			isLast
		)
	}

	companion object {
		fun fromEntity(cityEntity: CityEntity): CityModel {
			return CityModel(
				cityEntity.id,
				cityEntity.name,
				cityEntity.latitude,
				cityEntity.longitude,
				cityEntity.sort,
				cityEntity.isLast
			)
		}
	}
}
