package com.lampotrias.seeweather.presentation.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lampotrias.seeweather.data.city.database.CityStoredDao
import com.lampotrias.seeweather.data.weather.sources.database.AppDatabase
import com.lampotrias.seeweather.data.weather.sources.database.dao.ServerSyncStatusDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

	@Provides
	@Singleton
	fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
		return Room.databaseBuilder(
			appContext,
			AppDatabase::class.java,
			"weatherDb"
		).addCallback(object : RoomDatabase.Callback(){
			override fun onOpen(db: SupportSQLiteDatabase) {
				super.onOpen(db)
				db.execSQL("delete from server_status")
			}
		})
			.build()
	}

	@Provides
	fun provideChannelDao(appDatabase: AppDatabase): CityStoredDao {
		return appDatabase.cityStoredDao()
	}

	@Provides
	fun provideServerSyncDao(appDatabase: AppDatabase): ServerSyncStatusDao {
		return appDatabase.serverStatusDao()
	}
}