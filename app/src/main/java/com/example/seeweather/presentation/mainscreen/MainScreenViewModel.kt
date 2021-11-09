package com.example.seeweather.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainScreenViewModel: ViewModel() {

	private val _uiState = MutableStateFlow(State.INITIAL)
	val uiState: StateFlow<State> = _uiState

	fun sendRequest() {
		viewModelScope.launch {
			if (Math.random().roundToInt() == 1) {
				_uiState.value = State.OK
			} else {
				_uiState.value = State.ERROR
			}
		}
	}
}

enum class State {
	INITIAL,
	OK,
	ERROR
}