package com.example.seeweather.presentation.citysearch.list

import com.example.seeweather.domain.model.LocationModel

interface CityListener {
	fun onSelect(city: LocationModel)
	fun onMap(city: LocationModel) {}
}