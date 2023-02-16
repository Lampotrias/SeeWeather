package com.example.seeweather.presentation.citysearch.list

import androidx.recyclerview.widget.RecyclerView
import com.example.seeweather.databinding.CityItemBinding
import com.example.seeweather.domain.model.LocationModel

class CityViewHolder(
	private val binding: CityItemBinding,
	private val listener: CityListener
) : RecyclerView.ViewHolder(binding.root) {
	fun bind(city: LocationModel) {
		binding.name.text = city.name
		binding.fullTitle.text = city.fullName

		binding.actionSelect.setOnClickListener {
			listener.onSelect(city)
		}
	}
}