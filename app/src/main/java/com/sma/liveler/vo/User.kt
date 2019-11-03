package com.sma.liveler.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") @Expose val firstName: String,
    val middleName: String,
    val LastName: String,
    @SerializedName("email") val email: String,
    val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("val groups: List<Group>,") val cPassword: String
)