package com.example.seeweather.presentation.cityscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seeweather.databinding.CityFragmentBinding
import com.example.seeweather.domain.model.LocationModel
import com.example.seeweather.presentation.cityscreen.list.CityAdapter
import com.example.seeweather.presentation.cityscreen.list.CityListener
import com.example.seeweather.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : Fragment() {

	private val viewModel: CityViewModel by viewModels()
	private var _binding: CityFragmentBinding? = null
	private val binding: CityFragmentBinding
		get() = _binding!!

	private val citiesAdapter = CityAdapter(object : CityListener {
		override fun onSelect(city: LocationModel) {
			viewModel.pickCity(city)
			parentFragmentManager.popBackStack()
		}
	})

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = CityFragmentBinding.inflate(layoutInflater)
		binding.citiesList.layoutManager = LinearLayoutManager(requireContext())
		binding.citiesList.adapter = citiesAdapter

		launchAndRepeatWithViewLifecycle {
			viewModel.uiState.collect { state ->
				when(state) {
					CityViewModel.State.INITIAL -> {

					}
					is CityViewModel.State.ErrorResult -> {
						Toast.makeText(requireContext(), "Error: ${state.e.message}", Toast.LENGTH_SHORT).show()
					}
					is CityViewModel.State.SuccessResult -> {
						citiesAdapter.setCities(state.result)
					}
				}
			}
		}

		binding.searchField.addTextChangedListener {
			it?.let {
				viewModel.searchCity(it.toString())
			}
		}

		return binding.root
	}
}
