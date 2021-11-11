package com.example.seeweather.data.sources.weatherapi

import com.example.seeweather.domain.CurrentWeatherModel
import com.example.seeweather.domain.RequestModel
import com.example.seeweather.domain.ResponseException
import com.example.seeweather.domain.WeatherRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class WeatherApiSource @Inject constructor(
	private val okHttpClient: OkHttpClient,
) : WeatherRepo {

	private val defaultDispatcher = Dispatchers.IO
	private val kJson = Json { ignoreUnknownKeys = true }

	override suspend fun getCurrentWeather(requestModel: RequestModel): Result<CurrentWeatherModel> {
		return withContext(defaultDispatcher) {
			val url = "http://api.weatherapi.com/v1/current.json?key=$KEY&q=${requestModel.city}"
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
								Result.success(obj.toDomainModel())
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
										val obj = kJson.decodeFromString<ErrorModel>(json.getString("error"))
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

	companion object {
		private const val KEY = "a987953f29d04d63937192801210811"
	}
}