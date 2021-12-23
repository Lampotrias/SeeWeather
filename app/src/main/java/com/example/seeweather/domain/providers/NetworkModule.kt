package com.example.seeweather.domain.providers

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