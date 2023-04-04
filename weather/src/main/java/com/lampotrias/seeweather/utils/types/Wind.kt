package com.lampotrias.seeweather.utils.types

import com.lampotrias.seeweather.domain.WindDirection

data class Wind(
	val speed: Speed,
	val direction: WindDirection
)
