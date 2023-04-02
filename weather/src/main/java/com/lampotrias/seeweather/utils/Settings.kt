package com.lampotrias.seeweather.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.preference.PreferenceManager
import com.lampotrias.seeweather.R
import com.lampotrias.seeweather.utils.types.units.SpeedUnit
import com.lampotrias.seeweather.utils.types.units.TempUnit

@SuppressLint("StaticFieldLeak")
object Settings {
	const val PREF_SPEED_KEY = "pref_speed"
	const val PREF_TEMP_KEY = "pref_temp"

	private var context: Context? = null

	val speedMeasure: SpeedUnit
		get() {
			return context?.let { context ->
				SpeedUnit.values().firstOrNull {
					PreferenceManager.getDefaultSharedPreferences(context).getString(
						PREF_SPEED_KEY, SpeedUnit.KPH.value
					) == it.name.lowercase()
				} ?: SpeedUnit.KPH
			} ?: SpeedUnit.KPH
		}

	val tempMeasure: TempUnit
		get() {
			return context?.let { context ->
				TempUnit.values().firstOrNull {
					PreferenceManager.getDefaultSharedPreferences(context).getString(
						PREF_TEMP_KEY, TempUnit.C.value
					) == it.name.lowercase()
				} ?: TempUnit.C
			} ?: TempUnit.C
		}

	fun init(context: Context) {
		this.context = context

		PreferenceManager.getDefaultSharedPreferences(context).apply {
			val editor = edit()
			var isModify = false

			if (!contains(PREF_SPEED_KEY)) {
				editor.putString(PREF_SPEED_KEY, SpeedUnit.KPH.value)
				isModify = true
			}

			if (!contains(PREF_TEMP_KEY)) {
				editor.putString(PREF_TEMP_KEY, TempUnit.C.value)
				isModify = true
			}

			if (isModify) {
				editor.apply()
			}
		}
	}

	fun getSpeedPref(): Map<String, String> {
		return SpeedUnit.values().associate {
			when (it) {
				SpeedUnit.MPH -> {
					Pair(it.value, context?.getString(R.string.speed_mph) ?: "")
				}

				SpeedUnit.KPH -> {
					Pair(it.value, context?.getString(R.string.speed_kph) ?: "")
				}
				SpeedUnit.MSEC -> {
					Pair(it.value, context?.getString(R.string.speed_msec) ?: "")
				}
			}
		}
	}

	fun getTempPref(): Map<String, String> {
		return TempUnit.values().associate {
			when (it) {
				TempUnit.F -> {
					Pair(it.value, context?.getString(R.string.temp_f) ?: "")
				}

				TempUnit.C -> {
					Pair(it.value, context?.getString(R.string.temp_c) ?: "")
				}
			}
		}
	}

}