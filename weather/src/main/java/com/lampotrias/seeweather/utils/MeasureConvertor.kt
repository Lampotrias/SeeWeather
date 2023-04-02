package com.lampotrias.seeweather.utils

import kotlin.math.roundToInt

object MeasureConvertor {
	fun cToF(celsius: Number): Int {
		return ((9 / 5 * celsius.toFloat()) + 32).roundToInt()
	}

	fun kmphToMph(kmph: Number): Float {
		return (0.6214 * kmph.toFloat()).toFloat()
	}
}
