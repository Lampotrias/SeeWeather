package com.lampotrias.seeweather.utils

import android.util.Log

object Utils {
	fun log(message: String?) {
		Log.w("qqqq", message ?: "NULL VALUE")
	}
}