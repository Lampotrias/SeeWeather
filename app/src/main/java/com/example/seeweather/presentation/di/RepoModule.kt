package com.example.seeweather.presentation.di

import com.example.seeweather.data.city.DefaultCityRepositoryStoredRepository
import com.example.seeweather.data.remotelocations.LocationRepository
import com.example.seeweather.data.weather.WeatherRepositoryImpl
import com.example.seeweather.domain.ICityStoredRepository
import com.example.seeweather.domain.ILocationRepo
import com.example.seeweather.domain.WeatherRepository
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