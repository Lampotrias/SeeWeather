package com.example.seeweather.data.city.sources

import com.example.seeweather.data.city.sources.network.osm.OSMLocationDataSource
import javax.inject.Inject

class LocationDataSourceFactory @Inject constructor() {
	fun create(): LocationDataSource {
		return OSMLocationDataSource()
	}
}