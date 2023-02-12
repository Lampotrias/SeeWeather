package com.example.seeweather.data.city.sources

import com.example.seeweather.data.city.model.LocationEntity

interface LocationDataSource {
	suspend fun searchCity(searchQuery: String): Result<List<LocationEntity>>
}