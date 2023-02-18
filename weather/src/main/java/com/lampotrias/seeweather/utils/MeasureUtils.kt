package com.lampotrias.seeweather.utils

import kotlin.math.roundToInt

object MeasureUtils {
	fun cToF(celsius: Number): Int {
		return ((9 / 5 * celsius.toFloat()) + 32).roundToInt()
	}

	fun kmphToMph(kmph: Number): Int {
		return ((0.6214 * kmph.toFloat()).roundToInt())
	}
}