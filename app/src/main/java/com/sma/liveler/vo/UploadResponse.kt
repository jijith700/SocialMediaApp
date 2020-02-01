package com.sma.liveler.vo

data class UploadResponse(
    val actualName: String,
    val `file`: String,
    val fileLocation: String,
    val fileName: String,
    val message: String,
    val response: String,
    val thumb: String,
    val thumbName: String,
    val type: String,
    val user: User
)