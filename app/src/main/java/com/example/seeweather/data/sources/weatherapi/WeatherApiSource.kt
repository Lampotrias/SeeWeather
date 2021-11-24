package com.example.seeweather.data.sources.weatherapi

import com.example.seeweather.data.WeatherRepoEntity
import com.example.seeweather.data.model.CurrentWeatherEntity
import com.example.seeweather.data.sources.weatherapi.model.ErrorModel
import com.example.seeweather.data.sources.weatherapi.model.WeatherApiCurrentModel
import com.example.seeweather.domain.ResponseException
import com.example.seeweather.domain.model.RequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class WeatherApiSource(private val okHttpClient: OkHttpClient) : WeatherRepoEntity {

	private val defaultDispatcher = Dispatchers.IO
	private val kJson = Json { ignoreUnknownKeys = true }

	override suspend fun getCurrentWeather(requestModel: RequestModel): Result<CurrentWeatherEntity> {
		return withContext(defaultDispatcher) {
			val url = "http://api.weatherapi.com/v1/current.json?key=$KEY&q=${requestModel.city}&lang=${requestModel.lang}"
			val request = Request.Builder()
				.url(url)
				.build()

			try {
				val response = okHttpClient.newCall(request).execute()
				response.body?.let { responseBody ->
					val responseData = responseBody.source().readUtf8()
					if (response.isSuccessful) {
						val json = JSONObject(responseData)
						return@withContext if (json.has("current")) {
							val obj =
								kJson.decodeFromString<WeatherApiCurrentModel>(json.getString("current"))
							try {
								Result.success(obj.toEntityModel(1))
							} catch (ex: JSONException) {
								ex.printStackTrace()
								Result.failure(ex)
							}
						} else {
							Result.failure(ResponseException(Int.MIN_VALUE, "unknown error"))
						}
					} else {
						val error = when (response.code) {
							400, 401, 403 -> {
								try {
									val json = JSONObject(responseData)
									if (json.has("error")) {
										val obj =
											kJson.decodeFromString<ErrorModel>(json.getString("error"))
										ResponseException(obj.code, obj.message)
									} else {
										ResponseException(Int.MIN_VALUE, "unknown error")
									}

								} catch (ex: JSONException) {
									ex.printStackTrace()
									ex
								}
							}
							else -> {
								ResponseException(Int.MIN_VALUE, "unknown error")
							}
						}
						return@withContext Result.failure(error)
					}
				} ?: kotlin.run {
					return@withContext Result.failure(
						ResponseException(
							Int.MIN_VALUE,
							"empty response"
						)
					)
				}

			} catch (ex: Exception) {
				return@withContext Result.failure(ex)
			}
		}
	}

//	override suspend fun getCurrentDayWeather(requestModel: RequestModel): Result<DayWeatherModel> {
//		TODO("Not yet implemented")
//	}

//	override suspend fun getDailyWeather(requestModel: RequestModel): Result<DailyWeatherModel> {
	/*return withContext(defaultDispatcher) {
		val url =
			"http://api.weatherapi.com/v1/forecast.json?key=$KEY&q=${requestModel.city}&days=10"
		val request = Request.Builder()
			.url(url)
			.build()

		try {
			val response = okHttpClient.newCall(request).execute()
			response.body?.let { responseBody ->
				val responseData = responseBody.source().readUtf8()
				if (response.isSuccessful) {
					val json = JSONObject(responseData)
					val currentWeather = if (json.has("current")) {
						val obj =
							kJson.decodeFromString<WeatherApiCurrentModel>(json.getString("current"))
						try {
							obj.toDomainModel()
						} catch (ex: JSONException) {
							ex.printStackTrace()
							return@withContext Result.failure(ex)
						}
					} else {
						WeatherApiCurrentModel()
					}



					val days = mutableListOf<WeatherApiDayModel>()
					val hours = mutableListOf<WeatherApiHourModel>()
					json.optJSONObject("forecast")?.optJSONArray("forecastday")?.let { jsDays ->
						for (i in 0 until jsDays.length()) {
							val dayInfo = jsDays.getJSONObject(i)
							days.add(kJson.decodeFromString<WeatherApiHourModel>(json.optString("hour")))
							dayInfo.optJSONArray("hour")?.let { jsHours ->
								for (j in 0 until jsHours) {
									hours.add(kJson.decodeFromString<WeatherApiHourModel>(json.optString("hour")))
								}
							}
						}
					}
					return@withContext Result.success(DailyWeatherModel(currentWeather, DaysWeatherWrapper(days), HoursWeatherWrapper(hours)))

				} else {
					val error = when (response.code) {
						400, 401, 403 -> {
							try {
								val json = JSONObject(responseData)
								if (json.has("error")) {
									val obj =
										kJson.decodeFromString<ErrorModel>(json.getString("error"))
									ResponseException(obj.code, obj.message)
								} else {
									ResponseException(Int.MIN_VALUE, "unknown error")
								}

							} catch (ex: JSONException) {
								ex.printStackTrace()
								ex
							}
						}
						else -> {
							ResponseException(Int.MIN_VALUE, "unknown error")
						}
					}
					return@withContext Result.failure(error)
				}
			} ?: kotlin.run {
				return@withContext Result.failure(
					ResponseException(
						Int.MIN_VALUE,
						"empty response"
					)
				)
			}

		} catch (ex: Exception) {
			return@withContext Result.failure(ex)
		}
	}*/
//	}

	companion object {
		private const val KEY = "a987953f29d04d63937192801210811"
	}
}