package com.example.seeweather.presentation.daylist

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seeweather.databinding.DayViewHolderBinding
import com.example.seeweather.domain.model.DayWeatherModel
import java.text.SimpleDateFormat

data class DaysFormatted(
	val date: String,
	val tempMin: Float,
	val tempMax: Float,
	val icon: Uri? = null
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
				icon = it.icon
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
		binding.day.text = daysFormatted.date
		binding.tempMax.text = daysFormatted.tempMax.toString()
		binding.tempMin.text = daysFormatted.tempMin.toString()
		binding.dayImage.setImageURI(daysFormatted.icon)
	}
}