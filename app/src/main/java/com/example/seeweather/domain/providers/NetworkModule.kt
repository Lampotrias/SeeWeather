package com.example.seeweather.domain.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

	@Provides
	@Singleton
	fun provideHttpClient(): OkHttpClient {
		return OkHttpClient()
	}
}