package com.example.seeweather.presentation.cityscreen.list

import androidx.recyclerview.widget.RecyclerView
import com.example.seeweather.databinding.CityItemBinding
import com.example.seeweather.domain.model.LocationModel

class CityViewHolder(private val binding: CityItemBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(locationModel: LocationModel) {
		binding.name.text = locationModel.name
		binding.fullTitle.text = locationModel.fullName
	}
}