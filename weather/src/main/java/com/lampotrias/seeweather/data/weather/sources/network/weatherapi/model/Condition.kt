package com.lampotrias.seeweather.data.weather.sources.network.weatherapi.model

import android.net.Uri
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Condition {
	var icon: String = ""
	var text: String = ""
	var code: Int = 0
	val imageUri: Uri
		get() {
			return Uri.parse("https://" + icon.replace("//", ""))
		}
}