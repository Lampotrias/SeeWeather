package com.example.seeweather.data.city

import com.example.seeweather.data.city.database.CityStoredDao
import com.example.seeweather.domain.ICityStoredRepository
import com.example.seeweather.domain.model.CityModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCityRepositoryStoredRepository @Inject constructor(
	private val cityStoredDao: CityStoredDao
) : ICityStoredRepository {
	private val defaultDispatcher = Dispatchers.IO
	override suspend fun removeCity(id: Int) {
		withContext(defaultDispatcher) {
			cityStoredDao.removeCity(id)
		}
	}

	override suspend fun addCity(cityModel: CityModel) {
		withContext(defaultDispatcher) {
			if (cityModel.isLast) {
				cityStoredDao.resetLast()
			}

			val newId = cityStoredDao.addCity(cityModel.toEntity())
			setLastIfNeeded(newId)
		}
	}

	private suspend fun setLastIfNeeded(cityId: Long) {
		if (cityStoredDao.getCities().size == 1) {
			setLastCity(cityId)
		}
	}

	override suspend fun setLastCity(id: Long) {
		withContext(defaultDispatcher) {
			cityStoredDao.setLastCitySafe(id)
		}
	}

	override suspend fun getCities(): List<CityModel> {
		return withContext(defaultDispatcher) {
			cityStoredDao.getCities().map { CityModel.fromEntity(it) }
		}
	}

	override suspend fun getLastCity(): CityModel? {
		return withContext(defaultDispatcher) {
			cityStoredDao.getLastCity()?.let {
				CityModel.fromEntity(it)
			}
		}
	}
}