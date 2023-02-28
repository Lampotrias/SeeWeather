package com.lampotrias.seeweather.presentation.citylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampotrias.seeweather.databinding.CityListFragmentBinding
import com.lampotrias.seeweather.presentation.mainscreen.MainScreenFragment
import com.lampotrias.seeweather.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityListFragment : Fragment() {

	private val viewModel: CityListViewModel by activityViewModels()
	private var _binding: CityListFragmentBinding? = null
	private val binding: CityListFragmentBinding
		get() = _binding!!

	private val adapter = CityListAdapter(
		onRemove = {
			viewModel.deleteCity(it)
		},
		onSelect = {
			viewModel.selectCity(it)
		}
	)

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = CityListFragmentBinding.inflate(layoutInflater)

		binding.list.layoutManager = LinearLayoutManager(requireContext())
		binding.list.adapter = adapter

		launchAndRepeatWithViewLifecycle {
			viewModel.uiState.collect { state ->
				adapter.setItems(state.cities)

				state.selectCity?.getContentIfNotHandled()?.let { selectedCity ->
					setFragmentResult(
						MainScreenFragment.RESULT_KEY_FROM_CITY_LIST, bundleOf(
						MainScreenFragment.BUNDLE_KEY_CITY to selectedCity
					))
					parentFragmentManager.popBackStack()
				}
			}
		}

		return binding.root
	}
}