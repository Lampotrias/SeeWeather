package com.lampotrias.seeweather.presentation.citysearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampotrias.seeweather.databinding.CityFragmentBinding
import com.lampotrias.seeweather.domain.model.LocationModel
import com.lampotrias.seeweather.presentation.citysearch.list.CityAdapter
import com.lampotrias.seeweather.presentation.citysearch.list.CityListener
import com.lampotrias.seeweather.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : Fragment() {

	private val viewModel: CityViewModel by activityViewModels()
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
