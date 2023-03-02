package com.lampotrias.seeweather.utils

import android.util.Log
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
			1,
			getCoordinatesString(city),
			"ru",
			Settings.tempMeasure,
			Settings.speedMeasure
		)
	}
}