package com.example.seeweather.data.model

import android.net.Uri
import com.example.seeweather.domain.model.CurrentWeatherModel
import com.example.seeweather.domain.model.RequestModel
import com.example.seeweather.domain.model.TempUnit

object EntityMapper {
	fun toCurrentModel(
		requestModel: RequestModel,
		entity: CurrentWeatherEntity
	): CurrentWeatherModel {
		return CurrentWeatherModel(
			if (requestModel.tempUnit == TempUnit.C) entity.tempC else entity.tempF,
			entity.text,
			requestModel.city,
			Uri.parse(entity.icon)
		)
	}
}