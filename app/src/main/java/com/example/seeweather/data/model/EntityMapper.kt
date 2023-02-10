package com.example.seeweather.data.model

import android.net.Uri
import com.example.seeweather.domain.WindDirection
import com.example.seeweather.domain.model.CurrentWeatherModel
import com.example.seeweather.domain.model.DayWeatherModel
import com.example.seeweather.domain.model.GeneralWeatherModel
import com.example.seeweather.domain.model.HourWeatherModel
import com.example.seeweather.domain.model.RequestModel
import com.example.seeweather.utils.MeasureUtils
import com.example.seeweather.utils.Settings
import com.example.seeweather.utils.Utils

object EntityMapper {
	fun toDomainModel(
		requestModel: RequestModel,
		entity: GeneralEntityWeatherModel
	): GeneralWeatherModel {

		val currentSec = System.currentTimeMillis() / 1000
		val rawHours = entity.hours.map { toHourDomainModel(it, requestModel) }

		val startActualHour = rawHours.indexOfFirst { it.date > currentSec - 3600 }
		val targetHours = if (startActualHour != -1) {
			rawHours.subList(startActualHour, rawHours.size).take(24)
		} else {
			emptyList()
		}

		return GeneralWeatherModel(
			toCurrentWeatherDomainModel(entity.currentWeatherModel, requestModel),
			entity.days.map { toDayDomainModel(it, requestModel) },
			rawHours,
			targetHours
		)
	}

	private fun toCurrentWeatherDomainModel(
		entity: CurrentWeatherEntity,
		requestModel: RequestModel,
	): CurrentWeatherModel {
		Utils.log("cur weather from: $entity}")
		return CurrentWeatherModel(
			temp = if (requestModel.tempUnit == Settings.Temp.C) entity.tempC else MeasureUtils.cToF(entity.tempC),
			textStatus = entity.text,
			city = requestModel.city,
			icon = Uri.parse(entity.icon),
			windPower = if (requestModel.speedUnit == Settings.Speed.KPH) entity.windPowerKph else MeasureUtils.kmphToMph(entity.windPowerKph),
			windDirection = WindDirection.valueOfOrDefault(entity.windDir, WindDirection.ERROR),
			pressure = entity.pressure,
			humidity = entity.humidity
		)
	}

	private fun toDayDomainModel(
		entity: DayEntity,
		requestModel: RequestModel,
	): DayWeatherModel {
		return DayWeatherModel(
			date = entity.date,
			icon = entity.icon,
			tempMax = if (requestModel.tempUnit == Settings.Temp.C) entity.tempMaxC else MeasureUtils.cToF(entity.tempMaxC),
			tempMin = if (requestModel.tempUnit == Settings.Temp.C) entity.tempMinC else MeasureUtils.cToF(entity.tempMinC),
			textStatus = entity.textStatus,
			windPower = if (requestModel.speedUnit == Settings.Speed.KPH) entity.windPowerKph else MeasureUtils.kmphToMph(
				entity.windPowerKph
			),
			sunrise = entity.sunrise,
			sunset = entity.sunset
		)
	}

	private fun toHourDomainModel(
		entity: HourEntity,
		requestModel: RequestModel,
	): HourWeatherModel {
		return HourWeatherModel(
			entity.icon,
			entity.date,
			if (requestModel.tempUnit == Settings.Temp.C) entity.tempC else MeasureUtils.cToF(entity.tempC)
		)
	}
}