package com.example.seeweather.presentation.citysearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seeweather.domain.ICityStoredRepository
import com.example.seeweather.domain.ILocationRepo
import com.example.seeweather.domain.model.CityModel
import com.example.seeweather.domain.model.LocationModel
import com.example.seeweather.utils.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
	private val locationRepository: ILocationRepo,
	private val storedCityRepository: ICityStoredRepository
) : ViewModel() {

	private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.INITIAL)
	val uiState: StateFlow<State> = _uiState

	fun searchCity(searchQuery: String) {
		if (searchQuery.length > 2) {
			viewModelScope.launch {
				withContext(Dispatchers.IO) {
					locationRepository.searchCity(searchQuery).fold(
						{
							_uiState.value = State.SuccessResult(it)
						},
						{
							_uiState.value = State.ErrorResult(it)
						})
				}
			}
		}
	}

	fun pickCity(city: LocationModel) {
		Settings.lastSelectedCity = city
		viewModelScope.launch {
			storedCityRepository.addCity(
				CityModel(
					name = city.name,
					latitude = city.latitude,
					longitude = city.longitude,
				)
			)
		}
	}

	sealed class State {
		object INITIAL : State()
		class SuccessResult(val result: List<LocationModel>) : State()
		class ErrorResult(val e: Throwable) : State()
	}
}