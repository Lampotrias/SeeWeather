package com.lampotrias.seeweather.utils.types

import com.lampotrias.seeweather.utils.types.units.TempUnit

data class Temperature(
	val value: Float,
	val tempUnit: TempUnit
)
