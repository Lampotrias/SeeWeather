package com.example.seeweather.presentation.cityscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seeweather.data.city.LocationRepository
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
	private val locationRepository: LocationRepository
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
	}

	sealed class State {
		object INITIAL : State()
		class SuccessResult(val result: List<LocationModel>) : State()
		class ErrorResult(val e: Throwable) : State()
	}

}