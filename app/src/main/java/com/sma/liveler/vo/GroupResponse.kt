package com.sma.liveler.vo


data class GroupResponse(
    val success: String,
    val user: UserDetails
)

data class Group(
    val created_at: String,
    val created_by: Int,
    val id: Int,
    val isAdmin: Int,
    val membersCount: Int,
    val name: String,
    val pivot: Pivot,
    val updated_at: String
)

data class Pivot(
    val group_id: Int,
    val user_id: Int
)