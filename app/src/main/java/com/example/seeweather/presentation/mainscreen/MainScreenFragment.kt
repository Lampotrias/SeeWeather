package com.example.seeweather.presentation.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.seeweather.databinding.MainScreenFragmentBinding
import com.example.seeweather.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment() {
	private val viewModel: MainScreenViewModel by viewModels()
	private var _binding: MainScreenFragmentBinding? = null
	private val binding: MainScreenFragmentBinding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = MainScreenFragmentBinding.inflate(layoutInflater)

		binding.weatherButton.setOnClickListener {
			viewModel.sendRequest("Kemer")
		}

		launchAndRepeatWithViewLifecycle {
			viewModel.uiState.collect { state ->
				if (state is State.SuccessResult) {
					Log.e("asdasdas OK", state.result.toString())
				} else if (state is State.ErrorResult) {
					Log.e("asdasdas ER", state.e.toString())
				}
			}
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()

		_binding = null
	}
}

