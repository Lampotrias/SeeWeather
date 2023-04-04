package com.lampotrias.seeweather.presentation.mainscreen.hourslist

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.seeweather.data.weather.model.WeatherConditions
import com.lampotrias.seeweather.databinding.HourViewHolderBinding
import com.lampotrias.seeweather.domain.model.HourWeatherModel
import com.lampotrias.seeweather.utils.Utils
import com.lampotrias.seeweather.utils.types.Temperature
import com.lampotrias.seeweather.utils.types.Wind
import java.text.SimpleDateFormat

data class HoursFormatted(
	val date: String,
	val temp: Temperature,
	val icon: Uri? = null,
	val isDay: Boolean,
	val weatherConditions: WeatherConditions? = null,
	val wind: Wind,
)

class HoursAdapter : RecyclerView.Adapter<HourViewHolder>() {
	private var items: List<HoursFormatted> = emptyList()
	private val formatter = SimpleDateFormat("HH:mm")

	@SuppressLint("NotifyDataSetChanged")
	fun setItems(newItems: List<HourWeatherModel>) {
		items = newItems.map {
			HoursFormatted(
				date = formatter.format(it.date * 1000),
				temp = it.temp,
				icon = it.icon,
				weatherConditions = it.weatherConditions,
				wind = it.wind,
				isDay = it.isDay
			)
		}
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
		return HourViewHolder(
			HourViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		)
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
		holder.bind(items[position])
	}
}

class HourViewHolder(private val binding: HourViewHolderBinding) :
	RecyclerView.ViewHolder(binding.root) {

	fun bind(hourEntity: HoursFormatted) {
		binding.time.text = hourEntity.date
		binding.temp.text = hourEntity.temp.value.toString()

		Utils.applyWeatherConditionIcon(
			binding.hourImage,
			hourEntity.isDay,
			hourEntity.weatherConditions,
			hourEntity.icon
		)

		binding.windPower.text = hourEntity.wind.speed.value.toString()
		binding.windDirectionIcon.rotation = 0f
	}
}
