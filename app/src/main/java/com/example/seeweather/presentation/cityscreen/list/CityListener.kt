package com.example.seeweather.presentation.cityscreen.list

import com.example.seeweather.domain.model.LocationModel

interface CityListener {
	fun onSelect(city: LocationModel)
	fun onMap(city: LocationModel) {}
}