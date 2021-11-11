package com.example.seeweather.presentation.mainscreen

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.seeweather.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenFragment: Fragment(R.layout.main_screen_fragment) {

	private val viewModel: MainScreenViewModel by viewModels()
	private lateinit var cityField: EditText
	private lateinit var submitButton: Button
	private lateinit var stateText: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState.collect { state ->
					val text = when (state) {
						State.INITIAL -> "INITIAL"
						State.OK -> "OK"
						State.ERROR -> "ERROR"
					}
					stateText.text = text
				}
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		cityField = view.findViewById(R.id.city_field)
		stateText = view.findViewById(R.id.state_text)
		submitButton = view.findViewById<Button>(R.id.submit_btn).apply {
			setOnClickListener { viewModel.sendRequest(cityField.text.toString()) }
		}
	}
}