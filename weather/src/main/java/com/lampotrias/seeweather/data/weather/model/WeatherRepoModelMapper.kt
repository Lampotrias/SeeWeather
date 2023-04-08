package com.lampotrias.seeweather.data.weather.model

import android.net.Uri
import com.lampotrias.seeweather.domain.WindDirection
import com.lampotrias.seeweather.domain.model.*
import com.lampotrias.seeweather.utils.MeasureConvertor
import com.lampotrias.seeweather.utils.Utils
import com.lampotrias.seeweather.utils.types.Distance
import com.lampotrias.seeweather.utils.types.Speed
import com.lampotrias.seeweather.utils.types.Temperature
import com.lampotrias.seeweather.utils.types.Wind
import com.lampotrias.seeweather.utils.types.units.DistanceUnit
import com.lampotrias.seeweather.utils.types.units.SpeedUnit
import com.lampotrias.seeweather.utils.types.units.TempUnit

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
			temp = Temperature(
				when(requestModel.tempUnit) {
					TempUnit.C -> entity.tempC
					TempUnit.F -> MeasureConvertor.cToF(entity.tempC)
				},
				requestModel.tempUnit
			),
			isDay = entity.isDay,
			weatherConditions = entity.weatherConditions,
			textStatus = entity.text,
			weatherIcon = Uri.parse(entity.iconUri),
			wind = Wind(
				Speed(
					when (requestModel.speedUnit) {
						SpeedUnit.MPH -> MeasureConvertor.kmphToMph(entity.windPowerKph)
						SpeedUnit.KPH -> entity.windPowerKph
						SpeedUnit.MSEC -> MeasureConvertor.kmphToMsec(entity.windPowerKph)
					}, requestModel.speedUnit
				),
				WindDirection.valueOfOrDefault(entity.windDir, WindDirection.ERROR),
			),
			pressure = entity.pressure,
			humidity = entity.humidity,
			precipitation = entity.precipitation,
			windGust = Speed(
				value = when (requestModel.speedUnit) {
					SpeedUnit.MPH -> MeasureConvertor.kmphToMph(entity.windGust)
					SpeedUnit.KPH -> entity.windGust
					SpeedUnit.MSEC -> MeasureConvertor.kmphToMsec(entity.windGust)
				},
				speedUnit = requestModel.speedUnit
			),
			uv = entity.uv,
			visibility = Distance(
				value = when(requestModel.distanceUnit) {
					DistanceUnit.Meter -> MeasureConvertor.kmToMeter(entity.visibility)
					DistanceUnit.Kilometres -> entity.visibility
					DistanceUnit.Miles -> MeasureConvertor.kmToMiles(entity.visibility)
				},
				distanceUnit = requestModel.distanceUnit
			),
			feelsLike = Temperature(
				when(requestModel.tempUnit) {
					TempUnit.C -> entity.feelsLike
					TempUnit.F -> MeasureConvertor.cToF(entity.feelsLike)
				},
				requestModel.tempUnit
			),
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
			tempMax = Temperature(
				when(requestModel.tempUnit) {
					TempUnit.C -> entity.tempMaxC
					TempUnit.F -> MeasureConvertor.cToF(entity.tempMaxC)
				},
				requestModel.tempUnit
			),
			tempMin = Temperature(
				when(requestModel.tempUnit) {
					TempUnit.C -> entity.tempMinC
					TempUnit.F -> MeasureConvertor.cToF(entity.tempMinC)
				},
				requestModel.tempUnit
			),
			textStatus = entity.textStatus,
			windPower = if (requestModel.speedUnit == SpeedUnit.KPH) {
				entity.windPowerKph
			} else {
				MeasureConvertor.kmphToMph(entity.windPowerKph)
			},
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
			temp = Temperature(
				when(requestModel.tempUnit) {
					TempUnit.C -> entity.tempC
					TempUnit.F -> MeasureConvertor.cToF(entity.tempC)
				},
				requestModel.tempUnit
			),
			chanceOfRain = 0,
			wind = Wind(
				Speed(
					when (requestModel.speedUnit) {
						SpeedUnit.MPH -> MeasureConvertor.kmphToMph(entity.windPowerKph)
						SpeedUnit.KPH -> entity.windPowerKph
						SpeedUnit.MSEC -> MeasureConvertor.kmphToMsec(entity.windPowerKph)
					}, requestModel.speedUnit
				),
				WindDirection.valueOfOrDefault(entity.windDir, WindDirection.ERROR),
			),
		)
	}
}
