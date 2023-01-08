package com.example.seeweather.domain

enum class WindDirection(val minDegree: Float, val maxDegree: Float) {
	ERROR(0f, 0f),
	N(348.75f, 11.25f),
	NNE(11.25f, 33.75f),
	NE(33.75f, 56.25f),
	ENE(56.25f, 78.75f),
	E(78.75f, 101.25f),
	ESE(101.25f, 123.75f),
	SE(123.75f, 146.25f),
	SSE(146.25f, 168.75f),
	S(168.75f, 191.25f),
	SSW(191.25f, 213.75f),
	SW(213.75f, 236.25f),
	WSW(236.25f, 258.75f),
	W(258.75f, 281.25f),
	WNW(281.25f, 303.75f),
	NW(303.75f, 326.25f),
	NNW(326.25f, 348.75f);

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