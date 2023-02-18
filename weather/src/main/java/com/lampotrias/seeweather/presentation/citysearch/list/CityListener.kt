package com.lampotrias.seeweather.presentation.citysearch.list

import com.lampotrias.seeweather.domain.model.LocationModel

interface CityListener {
	fun onSelect(city: LocationModel)
	fun onMap(city: LocationModel) {}
}