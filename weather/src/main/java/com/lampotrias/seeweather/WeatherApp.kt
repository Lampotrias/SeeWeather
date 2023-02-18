package com.lampotrias.seeweather

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.lampotrias.seeweather.utils.Settings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp: Application() {
	override fun onCreate() {
		super.onCreate()

		Fresco.initialize(this)
		Settings.init(this)
	}
}