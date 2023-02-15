package com.example.seeweather.data.remotelocations.sources

import com.example.seeweather.data.remotelocations.model.LocationEntity

interface LocationDataSource {
	suspend fun searchCity(searchQuery: String): Result<List<LocationEntity>>
}