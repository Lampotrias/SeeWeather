package com.example.seeweather.data.model

import android.net.Uri
import com.example.seeweather.domain.model.*
import com.example.seeweather.utils.MeasureUtils

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
			if (requestModel.tempUnit == TempUnit.C) entity.tempC else MeasureUtils.cToF(entity.tempC),
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
			if (requestModel.tempUnit == TempUnit.C) entity.tempMaxC else MeasureUtils.cToF(entity.tempMaxC),
			if (requestModel.tempUnit == TempUnit.C) entity.tempMinC else MeasureUtils.cToF(entity.tempMinC),
			entity.textStatus,
			if (requestModel.SpeedUnit == SpeedUnit.KPH) entity.windPowerKph else MeasureUtils.kmphToMph(
				entity.windPowerKph
			),
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
			if (requestModel.tempUnit == TempUnit.C) entity.tempC else MeasureUtils.cToF(entity.tempC)
		)
	}
}