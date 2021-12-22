package com.example.seeweather.data.model

import android.net.Uri
import com.example.seeweather.domain.model.*

object EntityMapper {
	fun toDomainModel(
		requestModel: RequestModel,
		entity: GeneralEntityWeatherModel
	): GeneralWeatherModel {

		return GeneralWeatherModel(
			toCurrentWeatherDomainModel(entity.currentWeatherModel, requestModel),
			entity.days.map { toDayDomainModel(it, requestModel) },
			entity.hours.map { toHourDomainModel(it, requestModel) }
		)
	}

	private fun toCurrentWeatherDomainModel(
		entity: CurrentWeatherEntity,
		requestModel: RequestModel,
	): CurrentWeatherModel {
		return CurrentWeatherModel(
			if (requestModel.tempUnit == TempUnit.C) entity.tempC else entity.tempF,
			entity.text,
			requestModel.city,
			Uri.parse(entity.icon)
		)
	}

	private fun toDayDomainModel(
		entity: DayEntity,
		requestModel: RequestModel,
	): DayWeatherModel {
		return DayWeatherModel(
			entity.date,
			entity.icon,
			if (requestModel.tempUnit == TempUnit.C) entity.tempMaxC else entity.tempMaxF,
			if (requestModel.tempUnit == TempUnit.C) entity.tempMinC else entity.tempMinF,
			entity.textStatus,
			if (requestModel.SpeedUnit == SpeedUnit.KPH) entity.windPowerKph else entity.windPowerMph,
			entity.sunrise,
			entity.sunset
		)
	}

	private fun toHourDomainModel(
		entity: HourEntity,
		requestModel: RequestModel,
	): HourWeatherModel {
		return HourWeatherModel(
			entity.icon,
			entity.date,
			if (requestModel.tempUnit == TempUnit.C) entity.tempC else entity.tempF
		)
	}
}