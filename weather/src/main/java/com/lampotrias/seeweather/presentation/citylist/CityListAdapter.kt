package com.lampotrias.seeweather.presentation.citylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.databinding.CityListItemBinding
import com.lampotrias.seeweather.domain.model.CityModel

class CityListAdapter(
	private val onRemove: (CityModel) -> Unit,
	private val onSelect: (CityModel) -> Unit
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
			onRemove, onSelect
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items[position]
		holder.bind(item)
	}

	override fun getItemCount(): Int = items.size

	class ViewHolder(
		private val binding: CityListItemBinding,
		private val onRemove: (CityModel) -> Unit,
		private val onSelect: (CityModel) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(cityModel: CityModel) {
			binding.name.text = cityModel.name
			binding.temp.text = "67C"
			binding.weatherText.text = "Hazy sunshine"
			binding.time.text = "25 февраля 18:33"

//			binding.actionDelete.setOnClickListener {
//				onRemove(cityModel)
//			}
//			binding.actionSelect.setOnClickListener {
//				onSelect(cityModel)
//			}
		}
	}

}