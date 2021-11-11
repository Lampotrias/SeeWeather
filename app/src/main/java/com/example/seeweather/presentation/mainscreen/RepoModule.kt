package com.example.seeweather.presentation.mainscreen

import com.example.seeweather.data.sources.weatherapi.WeatherApiSource
import com.example.seeweather.domain.WeatherRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepoModule {
	@Binds
	abstract fun bindRepository(impl: WeatherApiSource): WeatherRepo
}