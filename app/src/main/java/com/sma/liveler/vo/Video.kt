package com.sma.liveler.vo

data class Video(
    val title: String,
    val time: String,
    val videoUrl: String,
    val thumbnail: String,
    val posts: Posts,
    val user: User
)

data class Posts(
    val current_page: Int,
    val `data`: List<Post>,
    val first_page_url: String,
    val from: Any,
    val last_page: Int,
    val last_page_url: String,
    val next_page_url: String,
    val path: String,
    val per_page: Int,
    val prev_page_url: String,
    val to: Any,
    val total: Int
)

data class Profile(
    val about: String,
    val birth_place: String,
    val city: String,
    val cover_picture: String,
    val created_at: String,
    val date_of_birth: String,
    val gender: String,
    val id: Int,
    val last_name: String,
    val marital_status: String,
    val occupation: String,
    val phone_number: String,
    val profile_picture: String,
    val updated_at: String,
    val user_id: Int,
    val wallet_amount: String,
    val website: String
)

data class Settings(
    val locale: String
)