package com.sma.liveler.api

import com.google.gson.JsonObject
import com.sma.liveler.vo.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * The interface which provides methods to get result of webservices
 */
interface WebApiListener {

    @POST("register")
    fun register(@Body user: RequestBody): Observable<Response<RegistrationResponse>>

    @POST("login")
    fun login(@Body user: RequestBody): Observable<Response<LoginResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-posts")
    fun getPost(@Header("Authorization") token: String): Observable<Response<PostResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("like-post")
    fun likePost(
        @Header("Authorization") token: String,
        @Body postId: RequestBody
    ): Observable<Response<PostResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-groups")
    fun getGroups(@Header("Authorization") token: String): Observable<Response<GroupResponse>>

    @POST("create-group")
    fun createGroup(@Body groupName: RequestBody): Observable<Response<GroupResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("details")
    fun userDetails(@Body groupName: RequestBody): Observable<Response<JsonObject>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-video-posts")
    fun getVideoPost(@Header("Authorization") token: String): Observable<Response<VideoPostResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("ajax/get-today-video")
    fun getDailyVideo(@Header("Authorization") token: String): Observable<Response<TodayVideoResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-friend-requests")
    fun getFollowing(@Header("Authorization") token: String): Observable<Response<FollowingResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-friends")
    fun getFriends(
        @Header("Authorization") token: String,
        @Body postId: RequestBody
    ): Observable<Response<FriendsResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-friends")
    fun getFriends(
        @Header("Authorization") token: String
    ): Observable<Response<FriendsResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("unfriend")
    fun unFriend(
        @Header("Authorization") token: String,
        @Body userId: RequestBody
    ): Observable<Response<UnFriendResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("post-status")
    fun addNewPost(
        @Header("Authorization") token: String,
        @Body status: RequestBody
    ): Observable<Response<NewPostResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("set-bank-info")
    fun addBankAccount(
        @Header("Authorization") token: String,
        @Body status: RequestBody
    ): Observable<Response<BankResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-bank-info")
    fun getBankDetails(@Header("Authorization") token: String): Observable<Response<BankResponse>>

    @Multipart
    @POST("upload")
    fun uploadVideo(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Observable<Response<UploadResponse>>

    @Multipart
    @POST("upload")
    fun uploadVideoAd(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Observable<Response<UploadResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("details")
    fun getUserDetails(@Header("Authorization") token: String): Observable<Response<JsonObject>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("post-media-status")
    fun addNewMediaPost(
        @Header("Authorization") token: String,
        @Body status: RequestBody
    ): Observable<Response<NewPostResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("read-all-notification")
    fun readAllNotifications(@Header("Authorization") token: String): Observable<Response<JsonObject>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-user")
    fun getNotifications(@Header("Authorization") token: String): Observable<Response<NotificationResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("add-friend")
    fun addFriend(
        @Header("Authorization") token: String,
        @Body userId: RequestBody
    ): Observable<Response<JsonObject>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("accept-request")
    fun acceptFriendRequest(
        @Header("Authorization") token: String,
        @Body userId: RequestBody
    ): Observable<Response<JsonObject>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("cancel-request")
    fun cancelFriendRequest(
        @Header("Authorization") token: String,
        @Body userId: RequestBody
    ): Observable<Response<JsonObject>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-ads")
    fun getAds(@Header("Authorization") token: String): Observable<Response<MyAdResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("post-ad")
    fun postAd(
        @Header("Authorization") token: String,
        @Body videoDetails: RequestBody
    ): Observable<Response<PostAdResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-personal-info")
    fun getPersonalInfo(@Header("Authorization") token: String): Observable<Response<UserInfoResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("set-personal-info")
    fun setPersonalInfo(@Header("Authorization") token: String): Observable<Response<JsonObject>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("send-message")
    fun sendMessage(
        @Header("Authorization") token: String,
        @Body chat: RequestBody
    ): Observable<Response<SendMessageResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-messages")
    fun getMessage(
        @Header("Authorization") token: String,
        @Body userId: RequestBody
    ): Observable<Response<ReceiveMessageResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("all-users")
    fun getAllUsers(@Header("Authorization") token: String): Observable<Response<AllUsersResponse>>
}