package com.lampotrias.seeweather.presentation.citylist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.R
import com.lampotrias.seeweather.databinding.CityListItemBinding
import com.lampotrias.seeweather.utils.Utils
import java.text.SimpleDateFormat

class CityListAdapter(
	private val onRemove: (CityAdapterModel) -> Unit,
	private val onSelect: (CityAdapterModel) -> Unit
) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

	private val items = mutableListOf<CityAdapterModel>()

	@SuppressLint("NotifyDataSetChanged")
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
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items[position]

		holder.bind(item)
		holder.itemView.setOnClickListener {
			onSelect(item)
		}

		holder.itemView.setOnLongClickListener {
			val deleteDialogBuilder = AlertDialog.Builder(it.context)
				.setTitle("Do you want delete city?")
				.setPositiveButton(R.string.ok) { _, _ ->
					onRemove(item)
				}
				.setNegativeButton(R.string.cancel) { _, _ ->

				}
			deleteDialogBuilder.create().show()

			return@setOnLongClickListener true
		}
	}

	override fun getItemCount(): Int = items.size

	class ViewHolder(
		private val binding: CityListItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		private val dateFormat = SimpleDateFormat.getDateTimeInstance()

		fun bind(cityModel: CityAdapterModel) {
			cityModel.timeZone?.let {
				dateFormat.timeZone = it
			}
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