package com.lampotrias.seeweather.presentation.citysearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampotrias.seeweather.databinding.CitySearchFragmentBinding
import com.lampotrias.seeweather.domain.model.LocationModel
import com.lampotrias.seeweather.presentation.citysearch.list.CityAdapter
import com.lampotrias.seeweather.presentation.citysearch.list.CityListener
import com.lampotrias.seeweather.presentation.mainscreen.MainScreenFragment
import com.lampotrias.seeweather.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitySearchFragment : Fragment() {

	private val viewModel: CitySearchViewModel by activityViewModels()
	private var _binding: CitySearchFragmentBinding? = null
	private val binding: CitySearchFragmentBinding
		get() = _binding!!

	private val citiesAdapter = CityAdapter(object : CityListener {
		override fun onSelect(city: LocationModel) {
			viewModel.pickCity(city)

			activity?.supportFragmentManager?.setFragmentResult(
				MainScreenFragment.RESULT_KEY_FROM_CITY_LIST, bundleOf()
			)
		}
	})

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = CitySearchFragmentBinding.inflate(layoutInflater)
		binding.citiesSearchList.layoutManager = LinearLayoutManager(requireContext())
		binding.citiesSearchList.adapter = citiesAdapter

		launchAndRepeatWithViewLifecycle {
			viewModel.uiState.collect { state ->
				when(state) {
					CitySearchViewModel.State.INITIAL -> {

					}
					is CitySearchViewModel.State.ErrorResult -> {
						Toast.makeText(requireContext(), "Error: ${state.e.message}", Toast.LENGTH_SHORT).show()
					}
					is CitySearchViewModel.State.SuccessResult -> {
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
