package com.sma.liveler.vo

data class NotificationResponse(
    val user: User
)

data class FriendRequest(
    val email: String,
    val fpp: String,
    val id: Int,
    val name: String,
    val spp: String,
    val type: String,
    val user_first_id: Int,
    val user_second_id: Int
)
