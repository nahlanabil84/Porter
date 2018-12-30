package com.nabil.nahla.porter.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("token")
	val token: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(error)
		parcel.writeString(token)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResponseLogin> {
		override fun createFromParcel(parcel: Parcel): ResponseLogin {
			return ResponseLogin(parcel)
		}

		override fun newArray(size: Int): Array<ResponseLogin?> {
			return arrayOfNulls(size)
		}
	}
}