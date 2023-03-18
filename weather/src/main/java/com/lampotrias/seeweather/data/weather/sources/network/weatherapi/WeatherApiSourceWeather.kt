package com.lampotrias.seeweather.data.weather.sources.network.weatherapi

import com.lampotrias.seeweather.data.weather.WeatherDataSource
import com.lampotrias.seeweather.data.weather.model.*
import com.lampotrias.seeweather.data.weather.sources.database.dao.ServerSyncStatusDao
import com.lampotrias.seeweather.data.weather.sources.database.model.ServerSyncTable
import com.lampotrias.seeweather.data.weather.sources.network.weatherapi.model.*
import com.lampotrias.seeweather.domain.ResponseException
import com.lampotrias.seeweather.domain.model.RequestModel
import com.lampotrias.seeweather.utils.Utils
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class WeatherApiSourceWeather @Inject constructor(
	private val serverSyncStatusDao: ServerSyncStatusDao,
) : WeatherDataSource {

	private val okHttpClient = OkHttpClient()
	private val defaultDispatcher = Dispatchers.IO

	override suspend fun getDataWeather(requestModel: RequestModel): Result<GeneralWeatherEntity> {
		return withContext(defaultDispatcher) {
			val url =
				"http://api.weatherapi.com/v1/forecast.json?key=$KEY&q=${requestModel.city}&days=10"

			Utils.log("start network request: $url")
			val request = Request.Builder()
				.url(url)
				.build()

			try {
				val moshi: Moshi = Moshi.Builder().build()
				val response = okHttpClient.newCall(request).execute()
				Utils.log("we receive response: $url")
				response.body?.let { responseBody ->
					val responseData = responseBody.source().readUtf8()
					if (response.isSuccessful) {
						Utils.log("response SUCCESS, start parse")
						val json = JSONObject(responseData)
						val currentWeather: CurrentWeatherEntity = if (json.has("current")) {
							try {
								val jsonAdapter = moshi.adapter(WeatherApiCurrentModel::class.java)
								val model = jsonAdapter.fromJson(json.getString("current")) ?: throw JSONException("1")
								val weatherConditions = getWeatherConditions(model.condition.code)
								model.toEntityModel(weatherConditions)
							} catch (ex: JSONException) {
								ex.printStackTrace()
								return@withContext Result.failure(ex)
							}
						} else {
							throw ResponseException(Int.MIN_VALUE, "key `current` not found")
						}

						val days = mutableListOf<WeatherApiDayModel>()
						val hours = mutableListOf<WeatherApiHourModel>()
						json.optJSONObject("forecast")?.optJSONArray("forecastday")?.let { jsDays ->
							for (i in 0 until jsDays.length()) {
								val rootDayInfo = jsDays.getJSONObject(i)
								val dayInfo = rootDayInfo.getJSONObject("day")
								val dayAdapter = moshi.adapter(WeatherApiDayModel::class.java)
								dayAdapter.fromJson(dayInfo.toString())?.also { dayModel ->
									rootDayInfo.optJSONObject("astro")?.let { astro ->
										dayModel.sunrise = astro.optString("sunrise")
										dayModel.sunset = astro.optString("sunset")
									}
									dayModel.date = rootDayInfo.getLong("date_epoch")

									days.add(dayModel)
								}

								rootDayInfo.optJSONArray("hour")?.let { jsHours ->
									for (j in 0 until jsHours.length()) {
										val hourAdapter = moshi.adapter(WeatherApiHourModel::class.java)
										hourAdapter.fromJson(jsHours.getJSONObject(j).toString())?.also { hourModel ->
											hours.add(hourModel)
										}
									}
								}
							}
						}

						serverSyncStatusDao.updateStatus(
							ServerSyncTable(
								json.getJSONObject("location").getLong("localtime_epoch"),
								currentWeather.date
							)
						)

						return@withContext Result.success(
							GeneralWeatherEntity(
								currentWeather,
								days.map { it.toEntity(getWeatherConditions(it.condition.code)) },
								hours.map { it.toEntity(getWeatherConditions(it.condition.code)) }
							)
						)

					} else {
						Utils.log("RESPONSE FAIL")
						val error = when (response.code) {
							400, 401, 403 -> {
								try {
									val json = JSONObject(responseData)
									if (json.has("error")) {
										val errorAdapter = moshi.adapter(ErrorModel::class.java)
										val obj = errorAdapter.fromJson(json.getString("error"))
										if (obj != null) {
											ResponseException(obj.code, obj.message)
										} else {
											ResponseException(Int.MIN_VALUE, "unknown error")
										}
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

	override suspend fun getShortWeatherData(requestModel: RequestModel): Result<CurrentShortWeatherEntity> {
		return withContext(defaultDispatcher) {

			val url = "http://api.weatherapi.com/v1/current.json?key=$KEY&q=${requestModel.city}"
			Utils.log("start network request: $url")

			val request = Request.Builder()
				.url(url)
				.build()

			try {
				val moshi: Moshi = Moshi.Builder().build()
				val response = okHttpClient.newCall(request).execute()
				Utils.log("we receive response: $url")

				response.body?.let { responseBody ->
					val responseData = responseBody.source().readUtf8()
					if (response.isSuccessful) {
						Utils.log("response SUCCESS, start parse")
						val json = JSONObject(responseData)

						val locationWeather: WeatherLocationEntity = if (json.has("location")) {
							try {
								val jsonAdapter = moshi.adapter(WeatherApiLocationModel::class.java)
								val model = jsonAdapter.fromJson(json.getString("location"))
									?: throw JSONException("1")
								model.toEntityModel()
							} catch (ex: JSONException) {
								ex.printStackTrace()
								return@withContext Result.failure(ex)
							}
						} else {
							WeatherLocationEntity()
						}

						val currentWeather: CurrentWeatherEntity = if (json.has("current")) {
							try {
								val jsonAdapter = moshi.adapter(WeatherApiCurrentModel::class.java)
								val model = jsonAdapter.fromJson(json.getString("current"))
									?: throw JSONException("1")
								val weatherConditions = getWeatherConditions(model.condition.code)
								model.toEntityModel(weatherConditions)
							} catch (ex: JSONException) {
								ex.printStackTrace()
								return@withContext Result.failure(ex)
							}
						} else {
							throw ResponseException(Int.MIN_VALUE, "key `current` not found")
						}

						return@withContext Result.success(
							CurrentShortWeatherEntity(
								currentWeather,
								locationWeather,
							)
						)
					} else {
					Utils.log("RESPONSE FAIL")
					val error = when (response.code) {
						400, 401, 403 -> {
							try {
								val json = JSONObject(responseData)
								if (json.has("error")) {
									val errorAdapter = moshi.adapter(ErrorModel::class.java)
									val obj = errorAdapter.fromJson(json.getString("error"))
									if (obj != null) {
										ResponseException(obj.code, obj.message)
									} else {
										ResponseException(Int.MIN_VALUE, "unknown error")
									}
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
				} ?: run {
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

	override suspend fun getWeatherConditions(conditions: Any?): WeatherConditions? {

		// TODO Логировать исключения по несуществующим кодам
		return when (conditions?.toString()){
			"1000" -> WeatherConditions.SunnyClear
			"1003" -> WeatherConditions.PartlyCloudy
			"1006" -> WeatherConditions.Cloudy
			"1009" -> WeatherConditions.Overcast
			"1030" -> WeatherConditions.Mist
			"1063" -> WeatherConditions.PatchyRainPossible
			"1066" -> WeatherConditions.PatchySnowPossible
			"1069" -> WeatherConditions.PatchySleetPossible
			"1072" -> WeatherConditions.PatchyFreezingDrizzlePossible
			"1087" -> WeatherConditions.ThunderyOutbreaksPossible
			"1114" -> WeatherConditions.BlowingSnow
			"1117" -> WeatherConditions.Blizzard
			"1135" -> WeatherConditions.Fog
			"1147" -> WeatherConditions.FreezingFog
			"1150" -> WeatherConditions.PatchyLightDrizzle
			"1153" -> WeatherConditions.LightDrizzle
			"1168" -> WeatherConditions.FreezingDrizzle
			"1171" -> WeatherConditions.HeavyFreezingDrizzle
			"1180" -> WeatherConditions.PatchyLightRain
			"1183" -> WeatherConditions.LightRain
			"1186" -> WeatherConditions.ModerateRainAtTimes
			"1189" -> WeatherConditions.ModerateRain
			"1192" -> WeatherConditions.HeavyRainAtTimes
			"1195" -> WeatherConditions.HeavyRain
			"1198" -> WeatherConditions.LightFreezingRain
			"1201" -> WeatherConditions.ModerateOrHeavyFreezingRain
			"1204" -> WeatherConditions.LightSleet
			"1207" -> WeatherConditions.ModerateOrHeavySleet
			"1210" -> WeatherConditions.PatchyLightSnow
			"1213" -> WeatherConditions.LightSnow
			"1216" -> WeatherConditions.PatchyModerateSnow
			"1219" -> WeatherConditions.ModerateSnow
			"1222" -> WeatherConditions.PatchyHeavySnow
			"1225" -> WeatherConditions.HeavySnow
			"1237" -> WeatherConditions.IcePellets
			"1240" -> WeatherConditions.LightRainShower
			"1243" -> WeatherConditions.ModerateOrHeavyRainShower
			"1246" -> WeatherConditions.TorrentialRainShower
			"1249" -> WeatherConditions.LightSleetShowers
			"1252" -> WeatherConditions.ModerateOrHeavySleetShowers
			"1255" -> WeatherConditions.LightSnowShowers
			"1258" -> WeatherConditions.ModerateOrHeavySnowShowers
			"1261" -> WeatherConditions.LightShowersOfIcePellets
			"1264" -> WeatherConditions.ModerateOrHeavyShowersOfIcePellets
			"1273" -> WeatherConditions.PatchyLightRainWithThunder
			"1276" -> WeatherConditions.ModerateOrHeavyRainWithThunder
			"1279" -> WeatherConditions.PatchyLightSnowWithThunder
			"1282" -> WeatherConditions.ModerateOrHeavySnowWithThunder
			else -> null
		}
	}

	companion object {
		private const val KEY = "a987953f29d04d63937192801210811"
	}
}
