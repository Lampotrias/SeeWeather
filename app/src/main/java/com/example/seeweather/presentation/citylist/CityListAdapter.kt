package com.example.seeweather.presentation.citylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seeweather.databinding.CityListItemBinding
import com.example.seeweather.domain.model.CityModel

class CityListAdapter(
	private val onRemove: (CityModel) -> Unit
) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

	private val items = mutableListOf<CityModel>()

	fun setItems(items: List<CityModel>) {
		this.items.clear()
		this.items.addAll(items)
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		return ViewHolder(
			CityListItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			),
			onRemove
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items[position]
		holder.bind(item)
	}

	override fun getItemCount(): Int = items.size

	class ViewHolder(
		private val binding: CityListItemBinding,
		private val onRemove: (CityModel) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(cityModel: CityModel) {
			binding.name.text = cityModel.name
			binding.actionDelete.setOnClickListener {
				onRemove(cityModel)
			}
		}
	}

}