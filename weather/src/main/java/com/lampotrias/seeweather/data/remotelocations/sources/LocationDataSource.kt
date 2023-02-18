package com.lampotrias.seeweather.data.remotelocations.sources

import com.lampotrias.seeweather.data.remotelocations.model.LocationEntity

interface LocationDataSource {
	suspend fun searchCity(searchQuery: String): Result<List<LocationEntity>>
}