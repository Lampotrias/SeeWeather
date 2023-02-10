package com.example.seeweather.data.weather.sources.network.weatherapi.model

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
class Condition {
	var icon: String = ""
	var text: String = ""
	var code: Int = 0
	val imageUri: Uri
		get() {
			return Uri.parse("https://" + icon.replace("//", ""))
		}
}