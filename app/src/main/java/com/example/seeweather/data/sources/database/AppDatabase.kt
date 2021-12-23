package com.example.seeweather.data.sources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.seeweather.data.sources.database.dao.ServerSyncStatusDao
import com.example.seeweather.data.sources.database.model.ServerSyncTable

@Database(
	entities = [
		ServerSyncTable::class
	], version = 1
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun serverStatusDao(): ServerSyncStatusDao
}