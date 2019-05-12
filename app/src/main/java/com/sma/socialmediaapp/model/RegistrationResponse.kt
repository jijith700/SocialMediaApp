package com.sma.socialmediaapp.model

data class RegistrationResponse(
    val details: Details,
    val message: String,
    val success: Boolean
)

data class Details(
    val __v: Int,
    val _id: String,
    val avatar: String,
    val email: String,
    val name: String,
    val password: String,
    val role: Int,
    val userType: Int
)