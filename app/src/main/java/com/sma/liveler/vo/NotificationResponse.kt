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

data class UnreadNotification(
    val created_at: String,
    val `data`: Data,
    val id: String,
    val notifiable_id: Int,
    val notifiable_type: String,
    val type: String,
    val updated_at: String
)

data class Data(
    val adId: Int,
    val image: String,
    val message: String,
    val time: String,
    val type: String,
    val user_id: Int
)
