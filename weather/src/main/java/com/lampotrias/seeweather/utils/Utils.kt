package com.lampotrias.seeweather.utils

import android.net.Uri
import android.util.Log
import com.facebook.drawee.view.SimpleDraweeView
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.domain.model.CityModel
import com.lampotrias.seeweather.domain.model.RequestModel

object Utils {
	fun log(message: String?) {
		Log.w("qqqq", message ?: "NULL VALUE")
	}

	private fun getCoordinatesString(city: CityModel): String {
		return "${city.latitude},${city.longitude}"
	}

	fun makeRequestModel(city: CityModel): RequestModel {
		return RequestModel(
			getCoordinatesString(city),
			"ru",
			Settings.tempMeasure,
			Settings.speedMeasure
		)
	}

	fun applyWeatherConditionIcon(
		view: SimpleDraweeView,
		isDay: Boolean,
		weatherConditions: WeatherConditions?,
		imageUri: Uri?
	) {
		if (weatherConditions != null) {
			if (isDay) {
				view.setImageResource(weatherConditions.day)
			} else {
				view.setImageResource(weatherConditions.night)
			}
		} else {
			view.setImageURI(imageUri)
		}
	}
}