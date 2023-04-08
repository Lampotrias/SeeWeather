package com.lampotrias.seeweather.presentation.mainscreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.databinding.MainScreenFragmentBinding
import com.lampotrias.seeweather.presentation.mainscreen.daylist.DayListAdapter
import com.lampotrias.seeweather.presentation.mainscreen.hourslist.HoursAdapter
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

	override fun onAttach(context: Context) {
		super.onAttach(context)

		activity?.supportFragmentManager?.setFragmentResultListener(RESULT_KEY_RESELECT_TAB, this) { requestKey, _ ->
			if (requestKey == RESULT_KEY_RESELECT_TAB) {
				if (binding.scrollContainer.canScrollVertically(-1)) {
					binding.scrollContainer.smoothScrollTo(0, 0)
				} else {
					viewModel.loadLastCity()
				}
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = MainScreenFragmentBinding.inflate(layoutInflater)

		binding.swipeRefresh.setOnRefreshListener {
			viewModel.loadLastCity()
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
						currentTemp.text = weather.currentWeatherModel.temp.value.toString()
						weatherDescription.text = weather.currentWeatherModel.textStatus

						Utils.applyWeatherConditionIcon(
							weatherImage,
							weather.currentWeatherModel.isDay,
							weather.currentWeatherModel.weatherConditions,
							weather.currentWeatherModel.weatherIcon
						)

						tempMax.text = weather.days[0].tempMax.value.toString()
						tempMin.text = weather.days[0].tempMin.value.toString()
						sunset.text = sunDateFormatter.format(weather.days[0].sunset)
						sunrise.text = sunDateFormatter.format(weather.days[0].sunrise)
						hoursAdapter.setItems(weather.actualizeHours)
						daysAdapter.setItems(weather.days)

						currentWeatherView.setFeelsLike(weather.currentWeatherModel.feelsLike)
						currentWeatherView.setPrecipitation(weather.currentWeatherModel.precipitation)
						currentWeatherView.setWind(weather.currentWeatherModel.wind)
						currentWeatherView.setPressure(weather.currentWeatherModel.pressure)
						currentWeatherView.setHumidity(weather.currentWeatherModel.humidity)
						currentWeatherView.setCloudCover(weather.currentWeatherModel.cloud)
						currentWeatherView.setUvIndex(weather.currentWeatherModel.uv)
						currentWeatherView.setVisibilityDistance(weather.currentWeatherModel.visibility)
						currentWeatherView.setWindGust(weather.currentWeatherModel.windGust)

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
		const val RESULT_KEY_RESELECT_TAB = "RESULT_KEY_RESELECT_TAB"
		const val BUNDLE_KEY_CITY_ID = "BUNDLE_KEY_CITY_ID"
	}
}

