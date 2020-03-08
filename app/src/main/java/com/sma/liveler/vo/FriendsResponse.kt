package com.sma.liveler.vo

import android.os.Parcel
import android.os.Parcelable

data class FriendsResponse(
    val friends: List<Friend>,
    val user: User,
    val user_name: String
)

data class Friend(
    val about: String,
    val birth_place: String,
    val city: String,
    val cover_picture: String,
    val created_at: String,
    val date_of_birth: String,
    val email: String,
    val gender: String,
    val id: Int,
    val isOnline: Boolean,
    val last_message: String,
    val last_name: String,
    val marital_status: String,
    val name: String,
    val occupation: String,
    val phone_number: String,
    val profile_picture: String,
    val type: String,
    val updated_at: String,
    val user_first_id: Int,
    val user_id: Int,
    val user_second_id: Int,
    val wallet_amount: String,
    val website: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(about)
        parcel.writeString(birth_place)
        parcel.writeString(city)
        parcel.writeString(cover_picture)
        parcel.writeString(created_at)
        parcel.writeString(date_of_birth)
        parcel.writeString(email)
        parcel.writeString(gender)
        parcel.writeInt(id)
        parcel.writeByte(if (isOnline) 1 else 0)
        parcel.writeString(last_message)
        parcel.writeString(last_name)
        parcel.writeString(marital_status)
        parcel.writeString(name)
        parcel.writeString(occupation)
        parcel.writeString(phone_number)
        parcel.writeString(profile_picture)
        parcel.writeString(type)
        parcel.writeString(updated_at)
        parcel.writeInt(user_first_id)
        parcel.writeInt(user_id)
        parcel.writeInt(user_second_id)
        parcel.writeString(wallet_amount)
        parcel.writeString(website)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Friend> {
        override fun createFromParcel(parcel: Parcel): Friend {
            return Friend(parcel)
        }

        override fun newArray(size: Int): Array<Friend?> {
            return arrayOfNulls(size)
        }
    }
}

data class FollowingResponse(
    val requests: List<Request>,
    val user: User
)

data class Request(
    val email: String,
    val fpp: String,
    val id: Int,
    val name: String,
    val spp: String,
    val type: String,
    val user_first_id: Int,
    val user_second_id: Int
)