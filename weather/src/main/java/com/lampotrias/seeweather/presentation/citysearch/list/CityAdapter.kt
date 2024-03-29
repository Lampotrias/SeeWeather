package com.lampotrias.seeweather.presentation.citysearch.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.databinding.CitySearchItemBinding
import com.lampotrias.seeweather.domain.model.LocationModel

class CityAdapter(private val listener: CityListener) : RecyclerView.Adapter<CityViewHolder>() {
	private val cities = mutableListOf<LocationModel>()

	fun setCities(cities: List<LocationModel>) {
		this.cities.clear()
		this.cities.addAll(cities)
		notifyDataSetChanged()
	}
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
		return CityViewHolder(
			CitySearchItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			),
			listener
		)
	}

	override fun getItemCount(): Int {
		return cities.size
	}

	override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
		holder.bind(cities[position])
	}
}
