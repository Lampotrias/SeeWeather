package com.lampotrias.seeweather.presentation.citysearch.list

import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.databinding.CitySearchItemBinding
import com.lampotrias.seeweather.domain.model.LocationModel

class CityViewHolder(
	private val binding: CitySearchItemBinding,
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