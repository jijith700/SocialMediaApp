package com.sma.liveler.vo

data class AllUsersResponse(
    val user: User,
    val users: List<AllUsers>
)

data class AllUsers(
    val about: String,
    val avatar: String,
    val birth_place: String,
    val city: String,
    val cover_picture: String,
    val created_at: String,
    val date_of_birth: String,
    val email: String,
    val email_verified_at: String,
    val gender: String,
    val id: Int,
    val last_name: String,
    val marital_status: String,
    val name: String,
    val occupation: String,
    val password: String,
    val phone_number: String,
    val profile_picture: String,
    val remember_token: String,
    val role_id: Int,
    val settings: String,
    val type: String,
    val updated_at: String,
    val user_first_id: Int,
    val user_id: Int,
    val user_second_id: Int,
    val wallet_amount: String,
    val website: String
)