package com.lampotrias.seeweather.presentation.mainscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.seeweather.domain.ICityStoredRepository
import com.lampotrias.seeweather.domain.WeatherRepository
import com.lampotrias.seeweather.domain.model.CityModel
import com.lampotrias.seeweather.domain.model.WeatherForecastModel
import com.lampotrias.seeweather.utils.OneShotEvent
import com.lampotrias.seeweather.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val weatherRepository: WeatherRepository,
	private val cityStoredRepository: ICityStoredRepository
) : ViewModel() {

	private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
	val uiState: StateFlow<MainUiState> = _uiState

	fun loadLastCity() {
		_uiState.update {
			it.copy(isLoading = true)
		}
		viewModelScope.launch {
			cityStoredRepository.getLastCity()?.let { city ->
				sendRequest(city)
			}
		}
	}

	fun sendRequest(cityId: Int) {
		Utils.log("start request0: $cityId")
		viewModelScope.launch {
			Utils.log("start request1: $cityId")
			cityStoredRepository.getCityById(cityId)?.let {
				sendRequest(it)
			}
		}
	}

	fun sendRequest(city: CityModel) {
		Utils.log("start request0: $city")
		viewModelScope.launch {
			Utils.log("start request1: $city")

			val result = withContext(Dispatchers.IO) {
				val requestModel = Utils.makeRequestModel(city)
				Utils.log("start request1: $requestModel")
				weatherRepository.getWeatherForecast(requestModel)
			}
			Utils.log("after request")
			result.fold({ res ->
				Utils.log("cur weather to: $res}")
				_uiState.update {
					it.copy(
						isLoading = false,
						city = city.name,
						weatherForecastModel = res
					)
				}
			}) { throwable ->
				_uiState.update {
					it.copy(
						isLoading = false,
						error = OneShotEvent(throwable)
					)
				}
			}
		}
	}
}

data class MainUiState(
	val isLoading: Boolean = false,
	val city: String = "",
	val weatherForecastModel: WeatherForecastModel? = null,
	val error: OneShotEvent<Throwable>? = null
)

sealed class State {
	object INITIAL : State()
	class SuccessResult(val city: String, val result: WeatherForecastModel) : State()
	class ErrorResult(val e: Throwable) : State()
}