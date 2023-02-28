package com.lampotrias.seeweather.presentation.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.R
import com.lampotrias.seeweather.databinding.MainScreenFragmentBinding
import com.lampotrias.seeweather.domain.model.CityModel
import com.lampotrias.seeweather.presentation.citylist.CityListFragment
import com.lampotrias.seeweather.presentation.citysearch.CityFragment
import com.lampotrias.seeweather.presentation.mainscreen.daylist.DayListAdapter
import com.lampotrias.seeweather.presentation.mainscreen.hourslist.HoursAdapter
import com.lampotrias.seeweather.presentation.settngs.SettingsFragment
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

		setFragmentResultListener(RESULT_KEY_FROM_CITY_LIST) {requestKey, bundle ->
			if (requestKey == RESULT_KEY_FROM_CITY_LIST) {
				val city = bundle.getParcelable<CityModel>(BUNDLE_KEY_CITY) ?: return@setFragmentResultListener
				viewModel.sendRequest(city)
			}
		}
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

		binding.actionMap.setOnClickListener {
			parentFragmentManager
				.beginTransaction()
				.add(R.id.nav_host_fragment, CityFragment())
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

		launchAndRepeatWithViewLifecycle {
			viewModel.uiState.collect { state ->

				val sunDateFormatter = SimpleDateFormat("HH:mm")

				if (state is State.SuccessResult) {
					with(binding) {
						selectedCity.text = state.city
						currentTemp.text = state.result.currentWeatherModel.temp.toString()
						weatherDescription.text = state.result.currentWeatherModel.textStatus
						weatherImage.setImageURI(state.result.currentWeatherModel.icon)
						tempMax.text = state.result.days[0].tempMax.toString()
						tempMin.text = state.result.days[0].tempMin.toString()
						sunset.text = sunDateFormatter.format(state.result.days[0].sunset)
						sunrise.text = sunDateFormatter.format(state.result.days[0].sunrise)
						hoursAdapter.setItems(state.result.actualizeHours)
						daysAdapter.setItems(state.result.days)
						windPowerValue.text = state.result.currentWeatherModel.windPower.toString()
						windDirectionValue.text = state.result.currentWeatherModel.windDirection.name
						pressureValue.text = state.result.currentWeatherModel.pressure.toInt().toString() + " hPa"
						humidityValue.text = state.result.currentWeatherModel.humidity.toString() + " %"
					}
					Log.e("asdasdas OK", state.result.toString())
				} else if (state is State.ErrorResult) {
					Log.e("asdasdas ER", state.e.toString())
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
		const val BUNDLE_KEY_CITY = "BUNDLE_KEY_CITY"
	}
}

