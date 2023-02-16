package com.example.seeweather.presentation.citylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seeweather.domain.ICityStoredRepository
import com.example.seeweather.domain.model.CityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
	private val storedCityRepository: ICityStoredRepository
) : ViewModel() {

	private val _uiState: MutableStateFlow<List<CityModel>> = MutableStateFlow(listOf())
	val uiState: StateFlow<List<CityModel>> = _uiState

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

	private suspend fun updateList() {
		_uiState.value = storedCityRepository.getCities()
	}
}