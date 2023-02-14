package com.example.seeweather.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.preference.PreferenceManager
import com.example.seeweather.R
import com.example.seeweather.domain.model.LocationModel
import com.squareup.moshi.Moshi

@SuppressLint("StaticFieldLeak")
object Settings {
	const val PREF_SPEED_KEY = "pref_speed"
	const val PREF_TEMP_KEY = "pref_temp"

	private var context: Context? = null
	private val adapter = Moshi.Builder().build().adapter(LocationModel::class.java)

	var lastSelectedCity: LocationModel?
		get() {
			val pref =
				context?.getSharedPreferences("qq", Context.MODE_PRIVATE)?.getString("last_city", "")
			return if (!pref.isNullOrBlank()) {
				adapter.fromJson(pref)
			} else {
				null
			}

		}
		set(value) {
			val pref = context?.getSharedPreferences("qq", Context.MODE_PRIVATE)
			pref?.edit()?.putString("last_city", adapter.toJson(value))?.apply()
		}

	val speedMeasure: Speed
		get() {
			return context?.let { context ->
				Speed.values().firstOrNull {
					PreferenceManager.getDefaultSharedPreferences(context).getString(
						PREF_SPEED_KEY, Speed.KPH.value
					) == it.name.lowercase()
				} ?: Speed.KPH
			} ?: Speed.KPH
		}

	val tempMeasure: Temp
		get() {
			return context?.let { context ->
				Temp.values().firstOrNull {
					PreferenceManager.getDefaultSharedPreferences(context).getString(
						PREF_TEMP_KEY, Temp.C.value
					) == it.name.lowercase()
				} ?: Temp.C
			} ?: Temp.C
		}

	fun init(context: Context) {
		this.context = context

		PreferenceManager.getDefaultSharedPreferences(context).apply {
			val editor = edit()
			var isModify = false

			if (!contains(PREF_SPEED_KEY)) {
				editor.putString(PREF_SPEED_KEY, Speed.KPH.value)
				isModify = true
			}

			if (!contains(PREF_TEMP_KEY)) {
				editor.putString(PREF_TEMP_KEY, Temp.C.value)
				isModify = true
			}

			if (isModify) {
				editor.apply()
			}
		}
	}

	fun getSpeedPref(): Map<String, String> {
		return Speed.values().associate {
			when (it) {
				Speed.MPH -> {
					Pair(it.value, context?.getString(R.string.speed_mph) ?: "")
				}

				Speed.KPH -> {
					Pair(it.value, context?.getString(R.string.speed_kph) ?: "")
				}
			}
		}
	}

	fun getTempPref(): Map<String, String> {
		return Temp.values().associate {
			when (it) {
				Temp.F -> {
					Pair(it.value, context?.getString(R.string.temp_f) ?: "")
				}

				Temp.C -> {
					Pair(it.value, context?.getString(R.string.temp_c) ?: "")
				}
			}
		}
	}

	enum class Speed(val value: String) {
		MPH("mph"),
		KPH("kph")
	}

	enum class Temp(val value: String) {
		C("c"),
		F("f")
	}
}