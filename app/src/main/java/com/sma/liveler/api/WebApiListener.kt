package com.sma.liveler.api

import com.sma.liveler.vo.GroupResponse
import com.sma.liveler.vo.LoginResponse
import com.sma.liveler.vo.PostResponse
import com.sma.liveler.vo.RegistrationResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

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
    fun likePost(@Header("Authorization") token: String, @Body postId: RequestBody): Observable<Response<PostResponse>>

    @Headers("Accept: application/json", "Content-type:application/json")
    @POST("get-groups")
    fun getGroups(@Header("Authorization") token: String): Observable<Response<GroupResponse>>

    @POST("create-group")
    fun createGroup(@Body groupName: RequestBody): Observable<Response<GroupResponse>>

}