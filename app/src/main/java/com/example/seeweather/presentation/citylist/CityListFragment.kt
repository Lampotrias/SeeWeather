package com.example.seeweather.presentation.citylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seeweather.databinding.CityListFragmentBinding
import com.example.seeweather.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityListFragment : Fragment() {

	private val viewModel: CityListViewModel by viewModels()
	private var _binding: CityListFragmentBinding? = null
	private val binding: CityListFragmentBinding
		get() = _binding!!

	private val adapter = CityListAdapter {
		viewModel.deleteCity(it)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = CityListFragmentBinding.inflate(layoutInflater)

		binding.list.layoutManager = LinearLayoutManager(requireContext())
		binding.list.adapter = adapter

		launchAndRepeatWithViewLifecycle {
			viewModel.uiState.collect { cities ->
				adapter.setItems(cities)
			}
		}

		return binding.root
	}
}