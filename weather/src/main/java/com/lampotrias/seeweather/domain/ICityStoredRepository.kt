package com.lampotrias.seeweather.domain

import com.lampotrias.seeweather.domain.model.CityModel


interface ICityStoredRepository {
	suspend fun removeCity(id: Int)
	suspend fun addCity(cityModel: CityModel)
	suspend fun setLastCity(id: Int)
	suspend fun getCities(): List<CityModel>
	suspend fun getLastCity(): CityModel?
	suspend fun getCityById(cityId: Int): CityModel?
}