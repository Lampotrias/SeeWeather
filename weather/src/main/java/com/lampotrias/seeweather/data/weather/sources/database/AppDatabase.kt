package com.lampotrias.seeweather.data.weather.sources.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.lampotrias.seeweather.data.city.database.CityStoredDao
import com.lampotrias.seeweather.data.city.model.CityEntity
import com.lampotrias.seeweather.data.weather.sources.database.dao.ServerSyncStatusDao
import com.lampotrias.seeweather.data.weather.sources.database.model.ServerSyncTable

@Database(
	version = 2,
	entities = [
		ServerSyncTable::class,
		CityEntity::class
	],
	autoMigrations = [
		AutoMigration(from = 1, to = 2)
	],
	exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun serverStatusDao(): ServerSyncStatusDao
	abstract fun cityStoredDao(): CityStoredDao
}