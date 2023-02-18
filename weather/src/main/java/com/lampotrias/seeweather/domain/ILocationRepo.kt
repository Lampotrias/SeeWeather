package com.lampotrias.seeweather.domain

import com.lampotrias.seeweather.domain.model.LocationModel

interface ILocationRepo {
	suspend fun searchCity(searchQuery: String): Result<List<LocationModel>>
}