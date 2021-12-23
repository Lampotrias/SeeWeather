package com.example.seeweather.data.sources.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server_status")
class ServerSyncTable(
	@PrimaryKey(autoGenerate = true)
	var id: Int = 0,
	val localLastUpdate: Long = 0,
	val remoteLastUpdate: Long = 0
)