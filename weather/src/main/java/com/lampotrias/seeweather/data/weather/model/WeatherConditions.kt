package com.lampotrias.seeweather.data.weather.model

import com.lampotrias.seeweather.R

enum class WeatherConditions(val day: Int, val night: Int) {
	SunnyClear(R.drawable.ic_w_sunny, R.drawable.ic_w_clean_night), // 113
	PartlyCloudy(R.drawable.ic_w_particle_cloud_day, R.drawable.ic_w_particle_cloud_night), // 116
	Cloudy(R.drawable.ic_w_cloud, R.drawable.ic_w_cloud), // 119
	Overcast(R.drawable.ic_w_double_cloud, R.drawable.ic_w_double_cloud), // 122
	Mist(R.drawable.ic_w_double_cloud, R.drawable.ic_w_double_cloud), // 143
	PatchyRainPossible(R.drawable.ic_w_cloud_rain_day, R.drawable.ic_w_cloud_rain_night), //176
	PatchySnowPossible(R.drawable.ic_w_cloud_snow_day, R.drawable.ic_w_cloud_snow_night), // 179
	PatchySleetPossible(R.drawable.ic_w_sleety_day, R.drawable.ic_w_sleety_night), // 182
	PatchyFreezingDrizzlePossible(R.drawable.ic_w_cloud_rain_day, R.drawable.ic_w_cloud_rain_night), // 185
	ThunderyOutbreaksPossible(R.drawable.ic_w_cloud_thunder_day, R.drawable.ic_w_cloud_thunder_night), // 200
	BlowingSnow(R.drawable.ic_w_snow_breeze, R.drawable.ic_w_snow_breeze), // 227
	Blizzard(R.drawable.ic_w_blizzard, R.drawable.ic_w_blizzard), // 230
	Fog(R.drawable.ic_w_double_cloud, R.drawable.ic_w_double_cloud), // 248
	FreezingFog(R.drawable.ic_w_double_cloud, R.drawable.ic_w_double_cloud), // 260
	PatchyLightDrizzle(R.drawable.ic_w_rainy, R.drawable.ic_w_rainy), // 263
	LightDrizzle(R.drawable.ic_w_rainy, R.drawable.ic_w_rainy), // 266
	FreezingDrizzle(R.drawable.ic_w_rainy, R.drawable.ic_w_rainy), // 281
	HeavyFreezingDrizzle(R.drawable.ic_w_rainy, R.drawable.ic_w_rainy), // 284
	PatchyLightRain(R.drawable.ic_w_cloud_rain_day, R.drawable.ic_w_cloud_rain_night), // 293
	LightRain(R.drawable.ic_w_cloud_rain, R.drawable.ic_w_cloud_rain), // 296
	ModerateRainAtTimes(R.drawable.ic_w_cloud_rain_day, R.drawable.ic_w_cloud_rain_night), // 299
	ModerateRain(R.drawable.ic_w_cloud_rain, R.drawable.ic_w_cloud_rain), // 302
	HeavyRainAtTimes(R.drawable.ic_w_rainy_day, R.drawable.ic_w_rainy_night), // 305
	HeavyRain(R.drawable.ic_w_rainy, R.drawable.ic_w_rainy), // 308
	LightFreezingRain(R.drawable.ic_w_cloud_rain, R.drawable.ic_w_cloud_rain), // 311
	ModerateOrHeavyFreezingRain(R.drawable.ic_w_sleety, R.drawable.ic_w_sleety), // 314
	LightSleet(R.drawable.ic_w_sleety, R.drawable.ic_w_sleety), // 317
	ModerateOrHeavySleet(R.drawable.ic_w_sleety, R.drawable.ic_w_sleety), // 320
	PatchyLightSnow(R.drawable.ic_w_cloud_snow_day, R.drawable.ic_w_cloud_snow_night), // 323
	LightSnow(R.drawable.ic_w_cloud_snow, R.drawable.ic_w_cloud_snow), // 326
	PatchyModerateSnow(R.drawable.ic_w_cloud_snow_day, R.drawable.ic_w_cloud_snow_night), // 329
	ModerateSnow(R.drawable.ic_w_cloud_snow, R.drawable.ic_w_cloud_snow), // 332
	PatchyHeavySnow(R.drawable.ic_w_cloud_snow_day, R.drawable.ic_w_cloud_snow_night), // 335
	HeavySnow(R.drawable.ic_w_snow, R.drawable.ic_w_snow), // 338
	IcePellets(R.drawable.ic_w_cloud_snow, R.drawable.ic_w_cloud_snow), // 350
	LightRainShower(R.drawable.ic_w_cloud_rain_day, R.drawable.ic_w_cloud_rain_night), // 353
	ModerateOrHeavyRainShower(R.drawable.ic_w_rainy_day, R.drawable.ic_w_rainy_night), // 356
	TorrentialRainShower(R.drawable.ic_w_rainy, R.drawable.ic_w_rainy), // 359
	LightSleetShowers(R.drawable.ic_w_sleety_day, R.drawable.ic_w_sleety_night), // 362
	ModerateOrHeavySleetShowers(R.drawable.ic_w_sleety_day, R.drawable.ic_w_sleety_night), // 365
	LightSnowShowers(R.drawable.ic_w_cloud_snow_day, R.drawable.ic_w_cloud_snow_night), // 368
	ModerateOrHeavySnowShowers(R.drawable.ic_w_cloud_snow_day, R.drawable.ic_w_cloud_snow_night), // 371
	LightShowersOfIcePellets(R.drawable.ic_w_cloud_snow_day, R.drawable.ic_w_cloud_snow_night), // 374
	ModerateOrHeavyShowersOfIcePellets(R.drawable.ic_w_cloud_snow_day, R.drawable.ic_w_cloud_snow_night), // 377
	PatchyLightRainWithThunder(R.drawable.ic_w_cloud_thunder_day, R.drawable.ic_w_cloud_thunder_night), // 386
	ModerateOrHeavyRainWithThunder(R.drawable.ic_w_thunder, R.drawable.ic_w_thunder), // 389
	PatchyLightSnowWithThunder(R.drawable.ic_w_thunder_snow, R.drawable.ic_w_thunder_snow), // 392
	ModerateOrHeavySnowWithThunder(R.drawable.ic_w_thunder_snow, R.drawable.ic_w_thunder_snow); // 395
}