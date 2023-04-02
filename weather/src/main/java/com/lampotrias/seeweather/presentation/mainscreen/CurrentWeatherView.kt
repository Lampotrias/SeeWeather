package com.lampotrias.seeweather.presentation.mainscreen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.lampotrias.seeweather.R

class CurrentWeatherView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

	lateinit var feelsLike: TextView
		private set
	lateinit var precipitation: TextView
		private set
	lateinit var cloudCover: TextView
		private set
	lateinit var wind: TextView
		private set
	lateinit var pressure: TextView
		private set
	lateinit var humidity: TextView
		private set
	lateinit var uvIndex: TextView
		private set
	lateinit var visibility: TextView
		private set
	lateinit var windGust: TextView
		private set

	init {
		LayoutInflater.from(context).inflate(R.layout.current_weather_view, this, true)
		onFinishInflate()
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		feelsLike = findViewById(R.id.feels_like_text)
		precipitation = findViewById(R.id.precipitation_text)
		cloudCover = findViewById(R.id.cloud_cover_text)
		wind = findViewById(R.id.wind_text)
		pressure = findViewById(R.id.pressure_text)
		humidity = findViewById(R.id.humidity_text)
		uvIndex = findViewById(R.id.uv_index_text)
		visibility = findViewById(R.id.visibility_text)
		windGust = findViewById(R.id.wind_gust_text)
	}

}
