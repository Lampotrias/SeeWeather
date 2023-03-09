package com.lampotrias.seeweather.presentation.citylist

import android.net.Uri
import com.lampotrias.seeweather.data.weather.model.WeatherConditions

class CityAdapterModel(
	val id: Int = 0,
	val name: String,
	val temp: Int = 0,
	val isDay: Boolean = true,
	val weatherIcon: Uri? = null,
	val weatherConditions: WeatherConditions? = null,
	val time: Long = 0,
	val textStatus: String = "",
	val sort: Int = 500,
)