package com.lampotrias.seeweather.data.remotelocations

import com.lampotrias.seeweather.data.remotelocations.sources.LocationDataSourceFactory
import com.lampotrias.seeweather.domain.ILocationRepo
import com.lampotrias.seeweather.domain.model.LocationModel
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