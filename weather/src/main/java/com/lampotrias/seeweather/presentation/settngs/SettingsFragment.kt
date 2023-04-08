package com.lampotrias.seeweather.presentation.settngs

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.elevation.SurfaceColors
import com.lampotrias.seeweather.R
import com.lampotrias.seeweather.utils.Settings
import com.lampotrias.seeweather.utils.Settings.PREF_DISTANCE_KEY
import com.lampotrias.seeweather.utils.Settings.PREF_SPEED_KEY
import com.lampotrias.seeweather.utils.Settings.PREF_TEMP_KEY


class SettingsFragment : PreferenceFragmentCompat() {

	private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
		findPreference<Preference>(key)?.let {
			it.summary = sharedPreferences.getString(key, "")
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val color = SurfaceColors.SURFACE_0.getColor(requireContext())
		view.setBackgroundColor(color)
	}

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		val context = preferenceManager.context
		val screen = preferenceManager.createPreferenceScreen(context)

		val measureUnits = PreferenceCategory(context).apply {
			key = "units_of_measure"
			title = context.getString(R.string.measure_units_category)
		}.also {
			screen.addPreference(it)
		}

		val distancePref = Settings.getDistancePref()
		ListPreference(context).apply {
			key = PREF_DISTANCE_KEY
			title = getString(R.string.pref_distance)
			entries = distancePref.values.toTypedArray()
			entryValues = distancePref.keys.toTypedArray()
			summary = Settings.distanceMeasure.value
		}.also {
			measureUnits.addPreference(it)
		}

		val speedPref = Settings.getSpeedPref()
		ListPreference(context).apply {
			key = PREF_SPEED_KEY
			title = getString(R.string.pref_speed)
			entries = speedPref.values.toTypedArray()
			entryValues = speedPref.keys.toTypedArray()
			summary = Settings.speedMeasure.value
		}.also {
			measureUnits.addPreference(it)
		}

		val tempPref = Settings.getTempPref()
		ListPreference(context).apply {
			key = PREF_TEMP_KEY
			title = getString(R.string.pref_temp)
			entries = tempPref.values.toTypedArray()
			entryValues = tempPref.keys.toTypedArray()
			summary = Settings.tempMeasure.value
		}.also {
			measureUnits.addPreference(it)
		}
		preferenceScreen = screen
	}

	override fun onResume() {
		super.onResume()
		preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(listener)
	}

	override fun onPause() {
		super.onPause()

		preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(listener)
	}
}
