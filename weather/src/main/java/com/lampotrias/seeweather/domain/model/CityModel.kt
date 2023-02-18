package com.lampotrias.seeweather.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.lampotrias.seeweather.data.city.model.CityEntity


data class CityModel(
	val id: Int = 0,
	val name: String,
	val latitude: Float,
	val longitude: Float,
	val sort: Int = 500,
	val isLast: Boolean = false
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readInt(),
		parcel.readString() ?: "",
		parcel.readFloat(),
		parcel.readFloat(),
		parcel.readInt(),
		parcel.readByte() != 0.toByte()
	) {
	}

	fun toEntity(): CityEntity {
		return CityEntity(
			id,
			sort,
			name,
			latitude,
			longitude,
			isLast
		)
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(id)
		parcel.writeString(name)
		parcel.writeFloat(latitude)
		parcel.writeFloat(longitude)
		parcel.writeInt(sort)
		parcel.writeByte(if (isLast) 1 else 0)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<CityModel> {
		override fun createFromParcel(parcel: Parcel): CityModel {
			return CityModel(parcel)
		}

		override fun newArray(size: Int): Array<CityModel?> {
			return arrayOfNulls(size)
		}

		fun fromEntity(cityEntity: CityEntity): CityModel {
			return CityModel(
				cityEntity.id,
				cityEntity.name,
				cityEntity.latitude,
				cityEntity.longitude,
				cityEntity.sort,
				cityEntity.isLast
			)
		}
	}
}
