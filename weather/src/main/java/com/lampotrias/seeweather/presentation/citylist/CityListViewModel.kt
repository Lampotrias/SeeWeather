package com.lampotrias.seeweather.presentation.citylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.seeweather.domain.ICityStoredRepository
import com.lampotrias.seeweather.domain.model.CityModel
import com.lampotrias.seeweather.utils.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
	private val storedCityRepository: ICityStoredRepository
) : ViewModel() {

	private val _uiState: MutableStateFlow<CityListUIState> = MutableStateFlow(CityListUIState())
	val uiState: StateFlow<CityListUIState> = _uiState

	init {
		viewModelScope.launch {
			updateList()
		}
	}

	fun deleteCity(cityModel: CityModel) {
		viewModelScope.launch {
			storedCityRepository.removeCity(cityModel.id)
			updateList()
		}
	}

	fun selectCity(cityModel: CityModel) {
		_uiState.update {
			it.copy(
				selectCity = OneShotEvent(cityModel)
			)
		}
	}

	private suspend fun updateList() {
		_uiState.update {
			it.copy(
				cities = storedCityRepository.getCities()
			)
		}
	}
}

data class CityListUIState(
	val cities: List<CityModel> = listOf(),
	val selectCity: OneShotEvent<CityModel>? = null
)