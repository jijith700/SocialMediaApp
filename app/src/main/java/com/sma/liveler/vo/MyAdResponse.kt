package com.sma.liveler.vo

data class MyAdResponse(
    val ads: List<Ad>,
    val user: User
)

data class Ad(
    val ad_cost: Any,
    val amount: Int,
    val created_at: String,
    val deleted_at: Any,
    val email: String,
    val firstname: String,
    val furl: String,
    val hash: String,
    val id: Int,
    val key: String,
    val name: String,
    val payment_reference: Any,
    val payment_status: String,
    val status: String,
    val surl: String,
    val thumbnail: String,
    val txnid: String,
    val updated_at: String,
    val user_id: Int,
    val video: String
)