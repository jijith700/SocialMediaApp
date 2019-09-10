package com.sma.liveler.vo



data class RegistrationResponse(
    val error: Error,
    val success: Success

)

data class Error(
    val email: List<String>
)

data class Success(
    val name: String,
    val token: String
)