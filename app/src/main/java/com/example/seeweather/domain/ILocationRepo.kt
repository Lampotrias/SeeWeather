package com.example.seeweather.domain

import com.example.seeweather.domain.model.LocationModel

interface ILocationRepo {
	suspend fun searchCity(searchQuery: String): Result<List<LocationModel>>
}