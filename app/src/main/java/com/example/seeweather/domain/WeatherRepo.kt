package com.example.seeweather.domain

interface WeatherRepo {
	suspend fun getCurrentWeather(requestModel: RequestModel): Result<CurrentWeatherModel>
}