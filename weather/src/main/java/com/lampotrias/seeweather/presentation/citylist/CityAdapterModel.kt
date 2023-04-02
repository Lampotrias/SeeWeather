package com.lampotrias.seeweather.presentation.citylist

import android.net.Uri
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.utils.types.Temperature
import java.util.*

class CityAdapterModel(
	val id: Int = 0,
	val name: String,
	val temp: Temperature? = null,
	val isDay: Boolean = true,
	val weatherIcon: Uri? = null,
	val weatherConditions: WeatherConditions? = null,
	val time: Long = 0,
	val timeZone: TimeZone? = null,
	val textStatus: String = "",
	val sort: Int = 500,
)