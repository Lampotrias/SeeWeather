package com.lampotrias.seeweather.presentation.di

import com.lampotrias.seeweather.data.city.DefaultCityRepositoryStoredRepository
import com.lampotrias.seeweather.data.remotelocations.LocationRepository
import com.lampotrias.seeweather.data.weather.WeatherRepositoryImpl
import com.lampotrias.seeweather.domain.ICityStoredRepository
import com.lampotrias.seeweather.domain.ILocationRepo
import com.lampotrias.seeweather.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {
	@Binds
	abstract fun bindRepositoryWeather(impl: WeatherRepositoryImpl): WeatherRepository

	@Binds
	abstract fun bindRepositoryLocation(impl: LocationRepository): ILocationRepo

	@Binds
	abstract fun bindRepositoryCities(impl: DefaultCityRepositoryStoredRepository): ICityStoredRepository
}