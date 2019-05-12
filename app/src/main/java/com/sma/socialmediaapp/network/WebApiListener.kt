package com.sma.socialmediaapp.network

import com.sma.socialmediaapp.model.LoginResponse
import com.sma.socialmediaapp.model.RegistrationResponse
import com.sma.socialmediaapp.model.User
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * The interface which provides methods to get result of webservices
 */
interface WebApiListener {
    /**
     * Get the list of the pots from the API
     */
//    @GET("/posts")
//    fun getPosts(): Observable<List<Post>>

    @POST("user/register")
    fun register(@Body user: User): Observable<Response<RegistrationResponse>>

    @POST("common/auth/login")
    fun login(@Body user: User): Observable<Response<LoginResponse>>
}