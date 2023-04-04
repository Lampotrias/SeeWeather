package com.lampotrias.seeweather.presentation.mainscreen

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.lampotrias.seeweather.R
import com.lampotrias.seeweather.utils.Chars
import com.lampotrias.seeweather.utils.types.Distance
import com.lampotrias.seeweather.utils.types.Speed
import com.lampotrias.seeweather.utils.types.Temperature
import com.lampotrias.seeweather.utils.types.Wind
import com.lampotrias.seeweather.utils.types.units.DistanceUnit
import com.lampotrias.seeweather.utils.types.units.SpeedUnit
import com.lampotrias.seeweather.utils.types.units.TempUnit

class CurrentWeatherView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

	private lateinit var feelsLikeView: TextView
	private lateinit var precipitationView: TextView
	private lateinit var cloudCoverView: TextView
	private lateinit var windView: TextView
	private lateinit var pressureView: TextView
	private lateinit var humidityView: TextView
	private lateinit var uvIndexView: TextView
	private lateinit var visibilityView: TextView
	private lateinit var windGustView: TextView

	init {
		LayoutInflater.from(context).inflate(R.layout.current_weather_view, this, true)
		onFinishInflate()
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		feelsLikeView = findViewById(R.id.feels_like_text)
		precipitationView = findViewById(R.id.precipitation_text)
		cloudCoverView = findViewById(R.id.cloud_cover_text)
		windView = findViewById(R.id.wind_text)
		pressureView = findViewById(R.id.pressure_text)
		humidityView = findViewById(R.id.humidity_text)
		uvIndexView = findViewById(R.id.uv_index_text)
		visibilityView = findViewById(R.id.visibility_text)
		windGustView = findViewById(R.id.wind_gust_text)
	}

	fun setFeelsLike(temperature: Temperature) {
		val units = when (temperature.tempUnit) {
			TempUnit.C -> Chars.Celsius
			TempUnit.F -> Chars.Fahrenheit
		}

		val value = temperature.value.toString()
		val spannableString = SpannableString(value + units)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		feelsLikeView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}

	fun setPrecipitation(precipitation: Float) {
		val value = precipitation.toInt().toString()
		val units = "mm"
		val spannableString = SpannableString(value + units)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		precipitationView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}

	fun setCloudCover(percent: Int) {
		val value = percent.toString()
		val units = "%"
		val spannableString = SpannableString(value + units)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		cloudCoverView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}

	fun setWind(wind: Wind) {
		val units = when (wind.speed.speedUnit) {
			SpeedUnit.MPH -> context.getString(R.string.speed_msec)
			SpeedUnit.KPH -> context.getString(R.string.speed_kmh)
			SpeedUnit.MSEC -> context.getString(R.string.speed_mph)
		}

		val value = wind.speed.value.toInt().toString()
		val spannableString = SpannableString(value + units)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		windView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}

	fun setPressure(bar: Float) {
		val value = bar.toInt().toString()
		val units = "mb"
		val spannableString = SpannableString(value + units)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		pressureView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}

	fun setHumidity(percent: Int) {
		val value = percent.toString()
		val units = "%"
		val spannableString = SpannableString(value + units)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		humidityView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}

	fun setUvIndex(index: Float) {
		val value = index.toInt().toString()

		val maxValue = "/11"
		val spannableString = SpannableString(value + maxValue)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		uvIndexView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}

	fun setVisibilityDistance(distance: Distance) {
		val units = when (distance.distanceUnit) {
			DistanceUnit.Meter -> context.getString(R.string.distance_meter)
			DistanceUnit.Kilometres -> context.getString(R.string.distance_km)
			DistanceUnit.Miles -> context.getString(R.string.distance_miles)
		}

		val value = distance.value.toInt().toString()
		val spannableString = SpannableString(value + units)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		visibilityView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}

	fun setWindGust(speed: Speed) {
		val units = when (speed.speedUnit) {
			SpeedUnit.MPH -> context.getString(R.string.speed_msec)
			SpeedUnit.KPH -> context.getString(R.string.speed_kmh)
			SpeedUnit.MSEC -> context.getString(R.string.speed_mph)
		}

		val value = speed.value.toInt().toString()
		val spannableString = SpannableString(value + units)
		spannableString.setSpan(
			RelativeSizeSpan(UNITS_SIZE_TEXT),
			value.length,
			spannableString.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		windGustView.setText(spannableString, TextView.BufferType.SPANNABLE)
	}
	
	companion object {
		private const val UNITS_SIZE_TEXT = 0.5f
	}
}
