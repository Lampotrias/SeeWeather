package com.example.seeweather.utils

object MeasureUtils {
	fun cToF(celsius: Number): Float {
		return (9 / 5 * celsius.toFloat()) + 32
	}

	fun kmphToMph(kmph: Number): Float {
		return (0.6214 * kmph.toFloat()).toFloat()
	}
}