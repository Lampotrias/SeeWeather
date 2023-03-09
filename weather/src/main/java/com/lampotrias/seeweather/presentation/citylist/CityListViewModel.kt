package com.lampotrias.seeweather.presentation.citylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.seeweather.domain.ICityStoredRepository
import com.lampotrias.seeweather.domain.WeatherRepository
import com.lampotrias.seeweather.utils.OneShotEvent
import com.lampotrias.seeweather.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
	private val storedCityRepository: ICityStoredRepository,
	private val weatherRepository: WeatherRepository
) : ViewModel() {

	private val _uiState: MutableStateFlow<CityListUIState> = MutableStateFlow(CityListUIState())
	val uiState: StateFlow<CityListUIState> = _uiState

	init {
		viewModelScope.launch {
			updateList()
		}
	}

	fun deleteCity(cityModel: CityAdapterModel) {
		viewModelScope.launch {
			storedCityRepository.removeCity(cityModel.id)
			updateList()
		}
	}

	fun selectCity(cityModel: CityAdapterModel) {
		_uiState.update {
			it.copy(
				selectCity = OneShotEvent(cityModel)
			)
		}
	}

	private suspend fun updateList() {
		val cities = storedCityRepository.getCities()
		_uiState.update {
			it.copy(cities = cities.map { city ->
				CityAdapterModel(
					id = city.id,
					name = city.name,
				)
			})
		}

		withContext(Dispatchers.IO) {
			val updatedCities: List<CityAdapterModel> = cities.map { cityModel ->
				val async = async {
					val requestModel = Utils.makeRequestModel(cityModel)
					return@async weatherRepository.getShortWeatherData(requestModel).getOrNull()?.let {
						CityAdapterModel(
							id = cityModel.id,
							name = cityModel.name,
							temp = it.currentWeatherModel.temp,
							time = it.weatherLocationModel.localtime,
							textStatus = it.currentWeatherModel.textStatus,
							sort = cityModel.sort,
							isDay = it.currentWeatherModel.isDay,
							weatherConditions = it.currentWeatherModel.weatherConditions,
							weatherIcon = it.currentWeatherModel.weatherIcon
						)
					}
				}
				async
			}.awaitAll().filterNotNull()

			updatedCities.forEach { newCity ->
				_uiState.update {
					it.copy(
						cities = it.cities.map { city ->
							if (city.id == newCity.id) {
								newCity
							} else
								city
						}
					)
				}
			}
		}
	}
}

data class CityListUIState(
	val cities: List<CityAdapterModel> = listOf(),
	val selectCity: OneShotEvent<CityAdapterModel>? = null
)