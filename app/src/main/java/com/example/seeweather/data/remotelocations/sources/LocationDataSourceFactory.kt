package com.example.seeweather.data.remotelocations.sources

import com.example.seeweather.data.remotelocations.sources.network.osm.OSMLocationDataSource
import javax.inject.Inject

class LocationDataSourceFactory @Inject constructor() {
	fun create(): LocationDataSource {
		return OSMLocationDataSource()
	}
}