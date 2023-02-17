package com.example.seeweather.domain

import com.example.seeweather.domain.model.CityModel

interface ICityStoredRepository {
	suspend fun removeCity(id: Int)
	suspend fun addCity(cityModel: CityModel)
	suspend fun setLastCity(id: Long)
	suspend fun getCities(): List<CityModel>
	suspend fun getLastCity(): CityModel?
}