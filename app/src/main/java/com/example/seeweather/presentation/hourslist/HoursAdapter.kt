package com.example.seeweather.presentation.hourslist

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seeweather.databinding.HourViewHolderBinding
import com.example.seeweather.domain.model.HourWeatherModel
import java.text.SimpleDateFormat

data class HoursFormatted(
	val date: String,
	val temp: Float,
	val icon: Uri? = null
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
				icon = it.icon
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
		binding.temp.text = hourEntity.temp.toString()
		binding.hourImage.setImageURI(hourEntity.icon)
	}
}
