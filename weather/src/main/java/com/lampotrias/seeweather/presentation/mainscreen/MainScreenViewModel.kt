package com.lampotrias.seeweather.presentation.mainscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.seeweather.domain.ICityStoredRepository
import com.lampotrias.seeweather.domain.WeatherRepository
import com.lampotrias.seeweather.domain.model.CityModel
import com.lampotrias.seeweather.domain.model.GeneralWeatherModel
import com.lampotrias.seeweather.domain.model.RequestModel
import com.lampotrias.seeweather.utils.Settings
import com.lampotrias.seeweather.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val weatherRepository: WeatherRepository,
	private val cityRepositoryStoredRepository: ICityStoredRepository
) : ViewModel() {

	private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.INITIAL)
	val uiState: StateFlow<State> = _uiState

	fun loadLastCity() {
		viewModelScope.launch {
			cityRepositoryStoredRepository.getLastCity()?.let { city ->
				sendRequest(city)
			}
		}
	}

	fun sendRequest(city: CityModel) {
		Utils.log("start request0: $city")
		viewModelScope.launch {
			Utils.log("start request1: $city")

			val result = withContext(Dispatchers.IO) {
				val requestModel = RequestModel(
					1,
					getCoordinatesString(city),
					"ru",
					Settings.tempMeasure,
					Settings.speedMeasure
				)
				Utils.log("start request1: $requestModel")
				weatherRepository.getWeather(requestModel)

			}
			Utils.log("after request")
			result.fold({
				Utils.log("cur weather to: $it}")
				_uiState.value = State.SuccessResult(city.name, it)
			}) {
				_uiState.value = State.ErrorResult(it)
			}
		}
	}

	private fun getCoordinatesString(city: CityModel): String {
		return "${city.latitude},${city.longitude}"
	}
}

sealed class State {
	object INITIAL : State()
	class SuccessResult(val city: String, val result: GeneralWeatherModel) : State()
	class ErrorResult(val e: Throwable) : State()
}