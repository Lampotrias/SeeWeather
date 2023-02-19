package com.lampotrias.seeweather.presentation.settngs

import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.elevation.SurfaceColors
import com.lampotrias.seeweather.R
import com.lampotrias.seeweather.utils.Settings
import com.lampotrias.seeweather.utils.Settings.PREF_SPEED_KEY
import com.lampotrias.seeweather.utils.Settings.PREF_TEMP_KEY


class SettingsFragment : PreferenceFragmentCompat() {

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

		val speedPref = Settings.getSpeedPref()
		ListPreference(context).apply {
			key = PREF_SPEED_KEY
			title = getString(R.string.pref_speed)
			entries = speedPref.values.toTypedArray()
			entryValues = speedPref.keys.toTypedArray()
		}.also {
			measureUnits.addPreference(it)
		}

		val tempPref = Settings.getTempPref()
		ListPreference(context).apply {
			key = PREF_TEMP_KEY
			title = getString(R.string.pref_temp)
			entries = tempPref.values.toTypedArray()
			entryValues = tempPref.keys.toTypedArray()
		}.also {
			measureUnits.addPreference(it)
		}
//		findPreference<ListPreference>("units_of_measure124")?.apply {
//			setDefaultValue("11")
//		}
		preferenceScreen = screen
	}
}