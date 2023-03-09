package com.lampotrias.seeweather.presentation.citylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.databinding.CityListItemBinding
import com.lampotrias.seeweather.utils.Utils
import java.text.SimpleDateFormat

class CityListAdapter(
	private val onRemove: (CityAdapterModel) -> Unit,
	private val onSelect: (CityAdapterModel) -> Unit
) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

	private val items = mutableListOf<CityAdapterModel>()

	fun setItems(items: List<CityAdapterModel>) {
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
		holder.itemView.setOnClickListener {
			onSelect(item)
		}
	}

	override fun getItemCount(): Int = items.size

	class ViewHolder(
		private val binding: CityListItemBinding,
		private val onRemove: (CityAdapterModel) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {
		private val dateFormat = SimpleDateFormat.getDateTimeInstance()

		fun bind(cityModel: CityAdapterModel) {
			binding.name.text = cityModel.name
			binding.temp.text = cityModel.temp.toString()
			binding.weatherText.text = cityModel.textStatus
			binding.time.text = dateFormat.format(cityModel.time * 1000)

			Utils.applyWeatherConditionIcon(
				binding.weatherImage,
				cityModel.isDay,
				cityModel.weatherConditions,
				cityModel.weatherIcon
			)
		}
	}

}