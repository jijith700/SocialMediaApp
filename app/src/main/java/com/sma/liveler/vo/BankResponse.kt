package com.sma.liveler.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankResponse(
    @SerializedName("bank-info") @Expose val bankInfo: BankInfo,
    val success: String
)

data class BankInfo(
    val avatar: String,
    val bank_details: BankDetails,
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val name: String,
    val role_id: Int,
    val settings: Settings,
    val updated_at: String
)

data class BankDetails(
    val account_holder_name: String,
    val account_number: String,
    val branch: String,
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val ifsc_number: String,
    val name_of_bank: String,
    val updated_at: String,
    val user_id: Int
)