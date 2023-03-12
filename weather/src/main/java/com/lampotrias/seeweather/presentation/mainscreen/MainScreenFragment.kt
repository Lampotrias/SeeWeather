package com.lampotrias.seeweather.presentation.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.R
import com.lampotrias.seeweather.databinding.MainScreenFragmentBinding
import com.lampotrias.seeweather.presentation.citylist.CityListFragment
import com.lampotrias.seeweather.presentation.citysearch.CityAddFragment
import com.lampotrias.seeweather.presentation.mainscreen.daylist.DayListAdapter
import com.lampotrias.seeweather.presentation.mainscreen.hourslist.HoursAdapter
import com.lampotrias.seeweather.presentation.settngs.SettingsFragment
import com.lampotrias.seeweather.utils.Utils
import com.lampotrias.seeweather.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class MainScreenFragment : Fragment() {
	private val viewModel: MainScreenViewModel by activityViewModels()
	private var _binding: MainScreenFragmentBinding? = null
	private val binding: MainScreenFragmentBinding
		get() = _binding!!

	private val hoursAdapter = HoursAdapter()
	private val daysAdapter = DayListAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = MainScreenFragmentBinding.inflate(layoutInflater)

		binding.actionRefresh.setOnClickListener {
			viewModel.loadLastCity()
		}

		binding.actionSettings.setOnClickListener {
			parentFragmentManager
				.beginTransaction()
				.add(R.id.nav_host_fragment, SettingsFragment())
				.addToBackStack("settings")
				.commit()
		}

		binding.swipeRefresh.setOnRefreshListener {
			viewModel.loadLastCity()
		}

		binding.actionMap.setOnClickListener {
			parentFragmentManager
				.beginTransaction()
				.add(R.id.nav_host_fragment, CityAddFragment())
				.addToBackStack("citysearch")
				.commit()
		}

		binding.actionCityList.setOnClickListener {
			parentFragmentManager
				.beginTransaction()
				.add(R.id.nav_host_fragment, CityListFragment())
				.addToBackStack("citylist")
				.commit()
		}

		binding.hoursList.adapter = hoursAdapter
		binding.hoursList.layoutManager = LinearLayoutManager(
			this@MainScreenFragment.requireContext(),
			RecyclerView.HORIZONTAL,
			false
		)

		binding.daysList.adapter = daysAdapter
		binding.daysList.layoutManager = LinearLayoutManager(
			this@MainScreenFragment.requireContext(),
			RecyclerView.HORIZONTAL,
			false
		)

		val sunDateFormatter = SimpleDateFormat("HH:mm")

		launchAndRepeatWithViewLifecycle {
			viewModel.uiState.collect { state ->

				binding.swipeRefresh.isRefreshing =
					state.isLoading && !binding.swipeRefresh.isRefreshing

				state.error?.getContentIfNotHandled()?.let { error ->
					Log.e("asdasdas ER", error.toString())
				}

				state.weatherForecastModel?.let { weather ->
					with(binding) {
						selectedCity.text = state.city
						currentTemp.text = weather.currentWeatherModel.temp.toString()
						weatherDescription.text = weather.currentWeatherModel.textStatus

						Utils.applyWeatherConditionIcon(
							weatherImage,
							weather.currentWeatherModel.isDay,
							weather.currentWeatherModel.weatherConditions,
							weather.currentWeatherModel.weatherIcon
						)

						tempMax.text = weather.days[0].tempMax.toString()
						tempMin.text = weather.days[0].tempMin.toString()
						sunset.text = sunDateFormatter.format(weather.days[0].sunset)
						sunrise.text = sunDateFormatter.format(weather.days[0].sunrise)
						hoursAdapter.setItems(weather.actualizeHours)
						daysAdapter.setItems(weather.days)
						windPowerValue.text = weather.currentWeatherModel.windPower.toString()
						windDirectionValue.text = weather.currentWeatherModel.windDirection.name
						pressureValue.text = weather.currentWeatherModel.pressure.toInt().toString() + " hPa"
						humidityValue.text = weather.currentWeatherModel.humidity.toString() + " %"
					}
					Log.e("asdasdas OK", weather.toString())
				}
			}
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()

		_binding = null
	}

	companion object {
		const val RESULT_KEY_FROM_CITY_LIST = "RESULT_KEY_FROM_CITY_LIST"
		const val REQUEST_KEY_RELOAD_WITH_CITY = "REQUEST_KEY_RELOAD_WITH_CITY"
		const val BUNDLE_KEY_CITY_ID = "BUNDLE_KEY_CITY_ID"
	}
}

