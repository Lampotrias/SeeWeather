package com.example.seeweather.data.model

import android.net.Uri
import com.example.seeweather.domain.model.CurrentWeatherModel
import com.example.seeweather.domain.model.RequestModel
import com.example.seeweather.domain.model.TempMetrics

object EntityMapper {
	fun toCurrentModel(
		requestModel: RequestModel,
		entity: CurrentWeatherEntity
	): CurrentWeatherModel {
		return CurrentWeatherModel(
			if (requestModel.tempMetrics == TempMetrics.C) entity.tempC else entity.tempF,
			entity.text,
			requestModel.city,
			Uri.parse(entity.icon)
		)
	}
}