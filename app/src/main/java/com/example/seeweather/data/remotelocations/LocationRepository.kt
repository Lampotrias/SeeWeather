package com.example.seeweather.data.remotelocations

import com.example.seeweather.data.remotelocations.sources.LocationDataSourceFactory
import com.example.seeweather.domain.ILocationRepo
import com.example.seeweather.domain.model.LocationModel
import javax.inject.Inject

class LocationRepository @Inject constructor(private val factory: LocationDataSourceFactory) :
	ILocationRepo {
	override suspend fun searchCity(searchQuery: String): Result<List<LocationModel>> {
		val dataSource = factory.create()
		return dataSource.searchCity(searchQuery).map {
			it.map { entity -> entity.toDomainModel() }
		}
	}
}