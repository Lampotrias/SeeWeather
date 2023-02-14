package com.example.seeweather.presentation.mainscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seeweather.domain.WeatherRepository
import com.example.seeweather.domain.model.GeneralWeatherModel
import com.example.seeweather.domain.model.RequestModel
import com.example.seeweather.utils.Settings
import com.example.seeweather.utils.Utils
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
	private val weatherRepository: WeatherRepository
) : ViewModel() {

	private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.INITIAL)
	val uiState: StateFlow<State> = _uiState

	fun sendRequest(city: String) {
		Utils.log("start request0: $city")
		viewModelScope.launch {
			Utils.log("start request1: $city")
			val lastSelectedCity = Settings.lastSelectedCity
			val c = lastSelectedCity?.let {
				"${it.latitude},${it.longitude}"
			} ?: city
			val result = withContext(Dispatchers.IO) {
				val requestModel = RequestModel(
					1,
					c,
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
				_uiState.value = State.SuccessResult(lastSelectedCity?.name ?: "unknown", it)
			}) {
				_uiState.value = State.ErrorResult(it)
			}
		}
	}

	override fun onCleared() {
		super.onCleared()
	}
}

sealed class State {
	object INITIAL : State()
	class SuccessResult(val city: String, val result: GeneralWeatherModel) : State()
	class ErrorResult(val e: Throwable) : State()
}