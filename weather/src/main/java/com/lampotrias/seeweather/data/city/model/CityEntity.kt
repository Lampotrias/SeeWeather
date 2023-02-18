package com.lampotrias.seeweather.data.city.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cities")
data class CityEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val sort: Int,
	val name: String,
	val latitude: Float,
	val longitude: Float,
	val isLast: Boolean
)
