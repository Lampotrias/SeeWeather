package com.example.seeweather

import android.app.Application
import com.example.seeweather.utils.Settings
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp: Application() {
	override fun onCreate() {
		super.onCreate()

		Fresco.initialize(this)
		Settings.init(this)
	}
}