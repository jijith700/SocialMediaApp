package com.sma.liveler.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    val middleName: String,
    val LastName: String,
    val phone: String,
    @SerializedName("name") @Expose val firstName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("val groups: List<Group>,") val cPassword: String

)/*{
    val avatar: String = ""
    val created_at: String = ""
    val email_verified_at: Any = ""
    val id: Int? = null
    val name: String = ""
    val profile: Profile? = null
    val role_id: Int? = null
    val settings: Settings? = null
    val updated_at: String? = null
}*/