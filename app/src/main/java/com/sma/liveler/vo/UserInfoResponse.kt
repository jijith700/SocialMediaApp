package com.sma.liveler.vo

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserInfoResponse(
    @SerializedName("personal-info")
    @Expose
    val personalInfo: PersonalInfo
)

@Parcelize
data class PersonalInfo(
    val avatar: String,
    val created_at: String,
    val email: String,
    val email_verified_at: String,
    val id: Int,
    val name: String,
    val profile: Profile,
    val role_id: Int,
    val settings: Settings,
    val updated_at: String
) : Parcelable
