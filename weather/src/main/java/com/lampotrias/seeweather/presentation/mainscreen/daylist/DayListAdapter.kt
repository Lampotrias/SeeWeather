package com.lampotrias.seeweather.presentation.mainscreen.daylist

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.databinding.DayViewHolderBinding
import com.lampotrias.seeweather.domain.model.DayWeatherModel
import com.lampotrias.seeweather.utils.Utils
import java.text.SimpleDateFormat

data class DaysFormatted(
	val date: String,
	val tempMin: Int,
	val tempMax: Int,
	val icon: Uri? = null,
	val weatherConditions: WeatherConditions? = null
)


class DayListAdapter : RecyclerView.Adapter<DayViewHolder>() {
	private var items: List<DaysFormatted> = emptyList()
	private val formatter = SimpleDateFormat("HH:mm")

	@SuppressLint("NotifyDataSetChanged")
	fun setItems(newItems: List<DayWeatherModel>) {
		items = newItems.map {
			DaysFormatted(
				date = formatter.format(it.date),
				tempMax = it.tempMax,
				tempMin = it.tempMin,
				icon = it.icon,
				weatherConditions = it.weatherConditions,
			)
		}
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
		return DayViewHolder(
			DayViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		)
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
		holder.bind(items[position])
	}
}

class DayViewHolder(private val binding: DayViewHolderBinding) :
	RecyclerView.ViewHolder(binding.root) {

	fun bind(daysFormatted: DaysFormatted) {
		with (daysFormatted) {
			binding.day.text = date
			binding.tempMax.text = tempMax.toString()
			binding.tempMin.text = tempMin.toString()
			Utils.applyWeatherConditionIcon(
				binding.dayImage,
				isDay = true,
				weatherConditions,
				icon
			)
		}
	}
}