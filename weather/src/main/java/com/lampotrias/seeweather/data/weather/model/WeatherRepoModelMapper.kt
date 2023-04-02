package com.lampotrias.seeweather.data.weather.model

import android.net.Uri
import com.lampotrias.seeweather.domain.WindDirection
import com.lampotrias.seeweather.domain.model.*
import com.lampotrias.seeweather.utils.MeasureUtils
import com.lampotrias.seeweather.utils.Settings
import com.lampotrias.seeweather.utils.Utils
import kotlin.math.roundToInt

object WeatherRepoModelMapper {
	fun toShortDomainModel(
		requestModel: RequestModel,
		entity: CurrentShortWeatherEntity
	): CurrentShortWeatherModel {
		return CurrentShortWeatherModel(
			toCurrentWeatherDomainModel(requestModel, entity.currentWeatherEntity),
			toWeatherLocationDomainModel(entity.weatherLocationEntity)
		)
	}

	fun toGeneralDomainModel(
		requestModel: RequestModel,
		entity: GeneralWeatherEntity
	): WeatherForecastModel {

		val currentSec = System.currentTimeMillis() / 1000
		val rawHours = entity.hours.map { toHourDomainModel(it, requestModel) }

		val startActualHour = rawHours.indexOfFirst { it.date > currentSec - 3600 }
		val targetHours = if (startActualHour != -1) {
			rawHours.subList(startActualHour, rawHours.size).take(24)
		} else {
			emptyList()
		}

		return WeatherForecastModel(
			toCurrentWeatherDomainModel(requestModel, entity.currentWeatherModel),
			entity.days.map { toDayDomainModel(it, requestModel) },
			rawHours,
			targetHours
		)
	}

	private fun toWeatherLocationDomainModel(
		entity: WeatherLocationEntity,
	): WeatherLocationModel {
		return WeatherLocationModel(
			name = entity.name,
			region = entity.region,
			country = entity.country,
			localtime = entity.localtime,
			timeZone = entity.timeZone
		)
	}
	private fun toCurrentWeatherDomainModel(
		requestModel: RequestModel,
		entity: CurrentWeatherEntity,
	): CurrentWeatherModel {
		Utils.log("cur weather from: $entity}")
		return CurrentWeatherModel(
			temp = if (requestModel.tempUnit == Settings.Temp.C) entity.tempC else MeasureUtils.cToF(
				entity.tempC
			),
			isDay = entity.isDay,
			weatherConditions = entity.weatherConditions,
			textStatus = entity.text,
			weatherIcon = Uri.parse(entity.iconUri),
			windPower = if (requestModel.speedUnit == Settings.Speed.KPH) entity.windPowerKph else MeasureUtils.kmphToMph(
				entity.windPowerKph
			).roundToInt(),
			windDirection = WindDirection.valueOfOrDefault(entity.windDir, WindDirection.ERROR),
			pressure = entity.pressure,
			humidity = entity.humidity,
			precipitation = entity.precipitation,
			windGust = if (requestModel.speedUnit == Settings.Speed.KPH) entity.windGust else MeasureUtils.kmphToMph(
				entity.windGust
			),
			uv = entity.uv,
			visibility = entity.visibility,
			feelsLike = entity.feelsLike,
			cloud = entity.cloud
		)
	}

	private fun toDayDomainModel(
		entity: DayEntity,
		requestModel: RequestModel,
	): DayWeatherModel {
		return DayWeatherModel(
			date = entity.date,
			icon = entity.icon,
			tempMax = if (requestModel.tempUnit == Settings.Temp.C) entity.tempMaxC else MeasureUtils.cToF(
				entity.tempMaxC
			),
			tempMin = if (requestModel.tempUnit == Settings.Temp.C) entity.tempMinC else MeasureUtils.cToF(
				entity.tempMinC
			),
			textStatus = entity.textStatus,
			windPower = if (requestModel.speedUnit == Settings.Speed.KPH) entity.windPowerKph else MeasureUtils.kmphToMph(
				entity.windPowerKph
			).roundToInt(),
			sunrise = entity.sunrise,
			sunset = entity.sunset,
			weatherConditions = entity.weatherConditions
		)
	}

	private fun toHourDomainModel(
		entity: HourEntity,
		requestModel: RequestModel,
	): HourWeatherModel {
		return HourWeatherModel(
			icon = entity.icon,
			date = entity.date,
			isDay = entity.isDay,
			weatherConditions = entity.weatherConditions,
			temp = if (requestModel.tempUnit == Settings.Temp.C) {
				entity.tempC
			} else {
				MeasureUtils.cToF(entity.tempC)
			},
			chanceOfRain = 0,
			windPower = if (requestModel.speedUnit == Settings.Speed.KPH) {
				entity.windPowerKph
			} else {
				MeasureUtils.kmphToMph(entity.windPowerKph).roundToInt()
			},
			windDirection = WindDirection.valueOfOrDefault(entity.windDir, WindDirection.ERROR),
			windDegree = entity.windDegree
		)
	}
}
