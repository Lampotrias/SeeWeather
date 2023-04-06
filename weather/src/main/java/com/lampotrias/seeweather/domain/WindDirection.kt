package com.lampotrias.seeweather.domain

enum class WindDirection(val degree: Float) {
	ERROR(0f),
	N(	0f),
	NNE(22.5f),
	NE(45f),
	ENE(67.5f),
	E(90f),
	ESE(112.5f),
	SE(135f),
	SSE(157.5f),
	S(180f),
	SSW(202.5f),
	SW(225f),
	WSW(247.5f),
	W(270f),
	WNW(292.5f),
	NW(315f),
	NNW(337.5f);

	companion object {
		fun valueOfOrDefault(value: String, default: WindDirection): WindDirection {
			return try {
				values().first { it.name.equals(value, true) }
			} catch (ex: NoSuchElementException) {
				default
			}
		}
	}
}