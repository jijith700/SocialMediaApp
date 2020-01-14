package com.sma.liveler.vo

data class UnFriendResponse(
    val requested_user: RequestedUser,
    val user: User
)

data class RequestedUser(
    val email: String,
    val id: Int,
    val name: String,
    val type: Any,
    val user_first_id: Any,
    val user_second_id: Any
)