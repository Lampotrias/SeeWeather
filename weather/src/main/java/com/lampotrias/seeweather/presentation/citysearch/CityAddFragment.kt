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
import com.lampotrias.seeweather.databinding.CityAddFragmentBinding
import com.lampotrias.seeweather.domain.model.LocationModel
import com.lampotrias.seeweather.presentation.citysearch.list.CityAdapter
import com.lampotrias.seeweather.presentation.citysearch.list.CityListener
import com.lampotrias.seeweather.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityAddFragment : Fragment() {

	private val viewModel: CityAddModel by activityViewModels()
	private var _binding: CityAddFragmentBinding? = null
	private val binding: CityAddFragmentBinding
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
		_binding = CityAddFragmentBinding.inflate(layoutInflater)
		binding.citiesList.layoutManager = LinearLayoutManager(requireContext())
		binding.citiesList.adapter = citiesAdapter

		launchAndRepeatWithViewLifecycle {
			viewModel.uiState.collect { state ->
				when(state) {
					CityAddModel.State.INITIAL -> {

					}
					is CityAddModel.State.ErrorResult -> {
						Toast.makeText(requireContext(), "Error: ${state.e.message}", Toast.LENGTH_SHORT).show()
					}
					is CityAddModel.State.SuccessResult -> {
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
