package com.lampotrias.seeweather.presentation.citysearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.seeweather.domain.ICityStoredRepository
import com.lampotrias.seeweather.domain.ILocationRepo
import com.lampotrias.seeweather.domain.model.CityModel
import com.lampotrias.seeweather.domain.model.LocationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CityAddModel @Inject constructor(
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
		viewModelScope.launch {
			storedCityRepository.addCity(
				CityModel(
					name = city.name,
					latitude = city.latitude,
					longitude = city.longitude,
					isLast = true
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