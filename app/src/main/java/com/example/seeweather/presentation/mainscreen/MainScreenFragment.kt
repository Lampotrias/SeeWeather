package com.example.seeweather.presentation.mainscreen

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.seeweather.R
import com.facebook.drawee.view.SimpleDraweeView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.sql.Date

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainScreenFragment: Fragment(R.layout.main_screen_fragment) {

	private val viewModel: MainScreenViewModel by viewModels()
	private lateinit var cityField: EditText
	private lateinit var submitButton: Button
	private lateinit var stateText: TextView
	private lateinit var icon: SimpleDraweeView
	private lateinit var temperature: TextView
	private lateinit var windDirection: TextView
	private lateinit var windPower: TextView
	private lateinit var timeOfDay: TextView
	private lateinit var lastUpdate: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState.collect { result ->
					val text = when (result) {
						State.INITIAL -> "INITIAL"
						is State.ErrorResult -> "ERROR"
						is State.SuccessResult -> {
							icon.setImageURI(result.result.icon)
							temperature.text = result.result.tempC.toString()
							windDirection.text = result.result.windDirection.toString()
							windPower.text = (result.result.windKph / 3.6).toString()
							timeOfDay.text = if (result.result.isDay) "day" else "night"
							lastUpdate.text = Date(result.result.lastUpdate * 1000).toString()
							"SUCCESS"
						}
					}
					stateText.text = text
				}
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		cityField = view.findViewById(R.id.city_field)
		stateText = view.findViewById(R.id.state_text)
		icon = view.findViewById(R.id.icon)
		temperature = view.findViewById(R.id.temperature)
		windDirection = view.findViewById(R.id.wind_direction)
		windPower = view.findViewById(R.id.wind_power)
		timeOfDay = view.findViewById(R.id.time_of_day)
		lastUpdate = view.findViewById(R.id.last_update)
		submitButton = view.findViewById<Button>(R.id.submit_btn).apply {
			setOnClickListener { viewModel.sendRequest(cityField.text.toString()) }
		}
	}
}