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
    @SerializedName("c_password") val cPassword: String
)