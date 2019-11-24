package com.sma.liveler.vo

data class FriendsResponse(
    val friends: List<Friend>,
    val user: User,
    val user_name: String
)

data class Friend(
    val about: Any,
    val birth_place: Any,
    val city: Any,
    val cover_picture: String,
    val created_at: String,
    val date_of_birth: String,
    val email: String,
    val gender: Any,
    val id: Int,
    val isOnline: Boolean,
    val last_message: String,
    val last_name: String,
    val marital_status: Any,
    val name: String,
    val occupation: Any,
    val phone_number: Any,
    val profile_picture: String,
    val type: String,
    val updated_at: String,
    val user_first_id: Int,
    val user_id: Int,
    val user_second_id: Int,
    val wallet_amount: String,
    val website: Any
)