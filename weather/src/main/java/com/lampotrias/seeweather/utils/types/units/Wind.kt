package com.lampotrias.seeweather.utils.types.units

import com.lampotrias.seeweather.domain.WindDirection
import com.lampotrias.seeweather.utils.types.Speed

data class Wind(
	val speed: Speed,
	val direction: WindDirection
)
