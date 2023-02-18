package com.lampotrias.seeweather.data.city.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.lampotrias.seeweather.data.city.model.CityEntity

@Dao
interface CityStoredDao {
	@Query("DELETE FROM cities WHERE id = :id")
	fun removeCity(id: Int)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addCity(cityModel: CityEntity): Long

	@Query("SELECT * FROM cities")
	fun getCities(): List<CityEntity>

	@Query("SELECT * FROM cities WHERE isLast = 1 LIMIT 1")
	fun getLastCity(): CityEntity?

	@Transaction
	fun setLastCitySafe(id: Long) {
		resetLast()
		setLastCityUnSafe(id)
	}

	@Query("UPDATE cities SET isLast = 1 WHERE id = :id")
	fun setLastCityUnSafe(id: Long)

	@Query("UPDATE cities SET isLast = 0")
	fun resetLast()
}