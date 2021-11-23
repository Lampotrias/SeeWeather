package com.example.seeweather.presentation.mainscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seeweather.domain.model.CurrentWeatherModel
import com.example.seeweather.domain.model.RequestModel
import com.example.seeweather.domain.WeatherRepo
import com.example.seeweather.domain.model.SpeedMetrics
import com.example.seeweather.domain.model.TempMetrics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val repository: WeatherRepo
) : ViewModel() {

	private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.INITIAL)
	val uiState: StateFlow<State> = _uiState

	fun sendRequest(city: String) {
		viewModelScope.launch {
			val result = repository.getCurrentWeather(RequestModel(city, "ru", TempMetrics.C, SpeedMetrics.KPH))
			result.fold({
				_uiState.value = State.SuccessResult(it)
			}) {
				_uiState.value = State.ErrorResult(it)
			}
		}
	}
}

sealed class State {
	object INITIAL : State()
	class SuccessResult(val result: CurrentWeatherModel) : State()
	class ErrorResult(val e: Throwable) : State()
}