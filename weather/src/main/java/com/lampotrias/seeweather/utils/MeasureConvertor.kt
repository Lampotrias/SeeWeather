package com.lampotrias.seeweather.utils

object MeasureConvertor {
	fun cToF(celsius: Number): Float {
		return ((9 / 5 * celsius.toFloat()) + 32)
	}

	fun kmphToMph(kmph: Number): Float {
		return (0.6214 * kmph.toFloat()).toFloat()
	}

	fun kmphToMsec(kmph: Number): Float {
		return kmph.toFloat() * 1000 / 3600
	}
}
