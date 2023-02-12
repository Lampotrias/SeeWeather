package com.example.seeweather.data.city

import com.example.seeweather.data.city.sources.LocationDataSourceFactory
import com.example.seeweather.domain.ILocationRepo
import com.example.seeweather.domain.model.LocationModel
import javax.inject.Inject

class LocationRepo @Inject constructor(private val factory: LocationDataSourceFactory) :
	ILocationRepo {
	override suspend fun searchCity(searchQuery: String): Result<List<LocationModel>> {
		val dataSource = factory.create()
		return dataSource.searchCity(searchQuery).map {
			it.map { entity -> entity.toDomainModel() }
		}
	}
}