package com.lampotrias.seeweather.presentation.di

import com.lampotrias.seeweather.data.cache.CacheWeather
import com.lampotrias.seeweather.data.cache.CacheWeatherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class CacheModule {
	@Binds
	abstract fun bindMyHelper(cacheWeatherImpl: CacheWeatherImpl): CacheWeather
}