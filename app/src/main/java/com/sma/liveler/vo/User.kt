package com.sma.liveler.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    val middleName: String,
    val lastName: String,
    val phone: String,
    @SerializedName("name") @Expose val firstName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("cpassword") val cPassword: String

) {

    val avatar: String = ""
    val created_at: String = ""
    val email_verified_at: String = ""
    val id: Int? = null
    val profile: Profile? = null
    val role_id: Int? = null
    val updated_at: String? = null
    val bank_details: BankDetails? = null
    val friendRequests: List<FriendRequest>? = null
    val unread_notifications: List<UnreadNotification>? = null

    constructor() : this("", "", "", "", "", "", "")
}