package com.example.seeweather.presentation.cityscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.seeweather.databinding.CityFragmentBinding

class CityFragment : Fragment() {

	private val viewModel: CityViewModel by viewModels()
	private var _binding: CityFragmentBinding? = null
	private val binding: CityFragmentBinding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = CityFragmentBinding.inflate(layoutInflater)

		return binding.root
	}
}