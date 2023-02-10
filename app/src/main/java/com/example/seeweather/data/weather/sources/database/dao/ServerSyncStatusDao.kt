package com.example.seeweather.data.weather.sources.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seeweather.data.weather.sources.database.model.ServerSyncTable

@Dao
interface ServerSyncStatusDao {
	@Query("SELECT * FROM server_status WHERE id = :serverId")
	fun getStatus(serverId: Int): ServerSyncTable?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun updateStatus(serverSyncTable: ServerSyncTable)
}