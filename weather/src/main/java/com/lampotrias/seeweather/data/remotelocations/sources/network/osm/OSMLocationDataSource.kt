package com.lampotrias.seeweather.data.remotelocations.sources.network.osm

import com.lampotrias.seeweather.data.remotelocations.model.LocationEntity
import com.lampotrias.seeweather.data.remotelocations.sources.LocationDataSource
import com.lampotrias.seeweather.utils.Utils
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import javax.inject.Inject

class OSMLocationDataSource @Inject constructor() : LocationDataSource {
	private val okHttpClient = OkHttpClient()
	override suspend fun searchCity(searchQuery: String): Result<List<LocationEntity>> {
		val finalUrl = BASE + searchQuery

		val request = Request.Builder()
			.url(finalUrl)
			.build()

		try {
			val response = okHttpClient.newCall(request).execute()
			Utils.log("we receive response location: $finalUrl")
			response.body?.let { responseBody ->
				val responseData = responseBody.source().readUtf8()
				if (response.isSuccessful) {
					val locations = mutableListOf<LocationEntity>()
					val jsonArray = JSONObject(responseData).getJSONArray("features")
					for (i in 0 until jsonArray.length()) {
						val json = jsonArray.getJSONObject(i)
						val props = json.getJSONObject("properties").getJSONObject("geocoding")
						val id = props.optInt("place_id")
						if (props.optString("type") != "city") {
							continue
						}
						val name = props.optString("name")
						val label = props.optString("label").replace("$name, ", "")
						val geometry = json.getJSONObject("geometry").optJSONArray("coordinates")
						val latitude = geometry?.getDouble(1) ?: 0.0
						val longitude = geometry?.getDouble(0) ?: 0.0
						locations.add(
							LocationEntity(
								id = id,
								name = name,
								fullName = label,
								latitude = latitude.toFloat(),
								longitude = longitude.toFloat()
							)
						)
					}
					return Result.success(locations)
				} else {
					return Result.failure(RuntimeException("Failture get locations: $responseData"))
				}
			}
			return Result.failure(RuntimeException("Failture get locations: response.body is null"))
		} catch (ex: Exception) {
			return Result.failure(ex)
		}
	}

	companion object {
		private const val BASE = "https://nominatim.openstreetmap.org/search?format=geocodejson&accept-language=ru&city="
	}
}