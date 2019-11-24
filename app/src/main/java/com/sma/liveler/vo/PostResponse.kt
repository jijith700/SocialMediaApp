package com.sma.liveler.vo

data class PostResponse(
    val posts: List<Post>,
    val post: List<Post>,
    val user: UserDetails,
    val success: String
)

data class Post(
    val access: String,
    val commentDisplayCount: Int,
    val comments: List<Comment>,
    val commentsCount: Int,
    val created_at: String,
    val deleted_at: Any,
    val group_id: Any,
    val id: Int,
    val image: Any,
    val isLiked: Boolean,
    val isOwnPost: Boolean,
    val lastComments: List<LastComment>,
    val lastLikes: List<LastLike>,
    val likeDisplayCount: Int,
    val likes: List<Like>,
    val likesCount: Int,
    val post: String,
    val postTime: String,
    val thumbnail: Any,
    val type: String,
    val updated_at: String,
    val user: UserDetails,
    val userName: String,
    val user_id: Int,
    val video: Any
)

data class Like(
    val created_at: String,
    val id: Int,
    val post_id: Int,
    val updated_at: String,
    val user: User,
    val userName: String,
    val user_id: Int
)

data class UserDetails(
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val groups: List<Group>,
    val id: Int,
    val name: String,
    val updated_at: String
)

data class LastLike(
    val created_at: String,
    val id: Int,
    val post_id: Int,
    val updated_at: String,
    val user: UserDetails,
    val userName: String,
    val user_id: Int
)

data class LastComment(
    val comment: String,
    val commentTime: String,
    val commenterName: String,
    val created_at: String,
    val id: Int,
    val post_id: Int,
    val updated_at: String,
    val user: User,
    val user_id: Int
)

data class Comment(
    val comment: String,
    val commentTime: String,
    val commenterName: String,
    val created_at: String,
    val id: Int,
    val post_id: Int,
    val updated_at: String,
    val user: User,
    val user_id: Int
)
