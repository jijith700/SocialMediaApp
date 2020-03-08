package com.sma.liveler.vo

data class ReceiveMessageResponse(
    val messages: List<ChatMessage>,
    val user: User
)

data class ChatMessage(
    val content: String,
    val created_at: String,
    val from_user: Int,
    val id: Int,
    val receiver_name: String,
    val receiver_profile_picture: String,
    val sender_name: String,
    val sender_profile_picture: String,
    val to_user: Int,
    val updated_at: String
)