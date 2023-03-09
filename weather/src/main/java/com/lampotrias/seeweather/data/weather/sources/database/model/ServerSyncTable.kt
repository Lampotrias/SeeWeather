package com.lampotrias.seeweather.data.weather.sources.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server_status")
class ServerSyncTable(
	val localLastUpdate: Long = 0,
	val remoteLastUpdate: Long = 0,
	@PrimaryKey(autoGenerate = true)
	var id: Int = 0,
)