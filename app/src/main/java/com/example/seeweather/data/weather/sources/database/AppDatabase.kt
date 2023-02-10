package com.example.seeweather.data.weather.sources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.seeweather.data.weather.sources.database.dao.ServerSyncStatusDao
import com.example.seeweather.data.weather.sources.database.model.ServerSyncTable

@Database(
	entities = [
		ServerSyncTable::class
	], version = 1
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun serverStatusDao(): ServerSyncStatusDao
}