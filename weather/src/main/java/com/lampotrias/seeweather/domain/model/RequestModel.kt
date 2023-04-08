package com.lampotrias.seeweather.domain.model

import com.lampotrias.seeweather.utils.types.units.DistanceUnit
import com.lampotrias.seeweather.utils.types.units.SpeedUnit
import com.lampotrias.seeweather.utils.types.units.TempUnit

data class RequestModel(
	val city: String,
	val lang: String,
	val tempUnit: TempUnit,
	val speedUnit: SpeedUnit,
	val distanceUnit: DistanceUnit
)
