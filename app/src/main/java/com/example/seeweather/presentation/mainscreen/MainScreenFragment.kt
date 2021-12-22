package com.example.seeweather.presentation.mainscreen

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
	private lateinit var pressure: TextView
	private lateinit var humidity: TextView
	private lateinit var cityText: TextView
	private lateinit var statusText: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState.collect { result ->
					val text = when (result) {
						State.INITIAL -> "INITIAL"
						is State.ErrorResult -> "ERROR"
						is State.SuccessResult -> {
							icon.setImageURI(result.result.currentWeatherModel.icon)
							temperature.text = result.result.currentWeatherModel.temp.toString()
							statusText.text = result.result.currentWeatherModel.textStatus
							cityText.text = result.result.currentWeatherModel.city
//							windDirection.text = result.result.windDirection.toString()
//							windPower.text = (result.result.windKph / 3.6).toString()
//							timeOfDay.text = if (result.result.isDay) "day" else "night"
//							lastUpdate.text = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Date(result.result.lastUpdateLocal * 1000))
//							pressure.text = (result.result.pressureMb * 0.750062).toInt().toString()
//							humidity.text = result.result.humidity.toString() + "%"
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
		pressure = view.findViewById(R.id.pressure)
		humidity = view.findViewById(R.id.humidity)
		cityText = view.findViewById(R.id.city)
		statusText = view.findViewById(R.id.status)
		submitButton = view.findViewById<Button>(R.id.submit_btn).apply {
			setOnClickListener { viewModel.sendRequest(cityField.text.toString()) }
		}
	}
}
