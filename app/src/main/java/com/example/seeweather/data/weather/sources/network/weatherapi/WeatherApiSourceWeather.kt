package com.example.seeweather.data.weather.sources.network.weatherapi

import com.example.seeweather.data.weather.WeatherDataSource
import com.example.seeweather.data.weather.model.CurrentWeatherEntity
import com.example.seeweather.data.weather.model.GeneralEntityWeatherModel
import com.example.seeweather.data.weather.sources.database.dao.ServerSyncStatusDao
import com.example.seeweather.data.weather.sources.database.model.ServerSyncTable
import com.example.seeweather.data.weather.sources.network.weatherapi.model.ErrorModel
import com.example.seeweather.data.weather.sources.network.weatherapi.model.WeatherApiCurrentModel
import com.example.seeweather.data.weather.sources.network.weatherapi.model.WeatherApiDayModel
import com.example.seeweather.data.weather.sources.network.weatherapi.model.WeatherApiHourModel
import com.example.seeweather.domain.ResponseException
import com.example.seeweather.domain.model.RequestModel
import com.example.seeweather.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class WeatherApiSourceWeather @Inject constructor(
	private val serverSyncStatusDao: ServerSyncStatusDao
) : WeatherDataSource {

	private val okHttpClient = OkHttpClient()
	private val defaultDispatcher = Dispatchers.IO
	private val kJson = Json { ignoreUnknownKeys = true }

	override suspend fun getDataWeather(requestModel: RequestModel): Result<GeneralEntityWeatherModel> {
		return withContext(defaultDispatcher) {
			val url =
				"http://api.weatherapi.com/v1/forecast.json?key=$KEY&q=${requestModel.city}&days=10"

			Utils.log("start network request: $url")
			val request = Request.Builder()
				.url(url)
				.build()

			try {
				val response = okHttpClient.newCall(request).execute()
				Utils.log("we receive response: $url")
				response.body?.let { responseBody ->
					val responseData = responseBody.source().readUtf8()
					if (response.isSuccessful) {
						Utils.log("response SUCCESS, start parse")
						val json = JSONObject(responseData)
						val currentWeather: CurrentWeatherEntity = if (json.has("current")) {
							try {
								kJson.decodeFromString<WeatherApiCurrentModel>(
									json.getString("current")
								).toEntityModel(1)
							} catch (ex: JSONException) {
								ex.printStackTrace()
								return@withContext Result.failure(ex)
							}
						} else {
							CurrentWeatherEntity()
						}

						val days = mutableListOf<WeatherApiDayModel>()
						val hours = mutableListOf<WeatherApiHourModel>()
						json.optJSONObject("forecast")?.optJSONArray("forecastday")?.let { jsDays ->
							for (i in 0 until jsDays.length()) {
								val rootDayInfo = jsDays.getJSONObject(i)
								val dayInfo = rootDayInfo.getJSONObject("day")

								days.add(
									kJson.decodeFromString<WeatherApiDayModel>(dayInfo.toString())
										.also {
											rootDayInfo.optJSONObject("astro")?.let { astro ->
												it.sunrise = astro.optString("sunrise")
												it.sunset = astro.optString("sunset")
											}
											it.date = rootDayInfo.getLong("date_epoch")
										}
								)
								rootDayInfo.optJSONArray("hour")?.let { jsHours ->
									for (j in 0 until jsHours.length()) {
										hours.add(
											kJson.decodeFromString(
												jsHours.getJSONObject(j).toString()
											)
										)
									}
								}
							}
						}

						serverSyncStatusDao.updateStatus(
							ServerSyncTable(
								requestModel.serverId,
								json.getJSONObject("location").getLong("localtime_epoch"),
								currentWeather.date
							)
						)

						return@withContext Result.success(
							GeneralEntityWeatherModel(
								currentWeather,
								days.map { it.toEntity() },
								hours.map { it.toEntity() }
							)
						)

					} else {
						Utils.log("RESPONSE FAIL")
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
					Utils.log("response error, empty response")
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
