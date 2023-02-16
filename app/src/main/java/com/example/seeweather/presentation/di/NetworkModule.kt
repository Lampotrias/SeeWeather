package com.example.seeweather.presentation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

//	@Provides
//	@Singleton
//	fun provideHttpClient(): OkHttpClient {
//		return OkHttpClient()
//	}
}