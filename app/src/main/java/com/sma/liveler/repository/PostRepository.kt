package com.sma.liveler.repository

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sma.liveler.R
import com.sma.liveler.api.ApiService
import com.sma.liveler.utils.*
import com.sma.liveler.vo.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber


class PostRepository(var context: Context) {

    var loading = MutableLiveData<Int>()
    var success = MutableLiveData<Boolean>()
    var errrorMessage = MutableLiveData<String>()
    var posts = MutableLiveData<List<Post>>()
    var friends = MutableLiveData<List<Friend>>()

    /**
     * Variable hold the object of ApiService and the it will initialized here.
     */
    private val apiService by lazy {
        ApiService.create()
    }

    /**
     * Method to get  all posts.
     */
    fun getPost() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)
        apiService.getPost(String.format(BEARER, token)).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<PostResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<PostResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        posts.value = t.body()?.posts
                        success.value = true
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_email_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    loading.value = View.GONE
                }
            })
    }

    /**
     * Method to like a posts.
     */
    fun likePost(postId: Int, userId: Int) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(POST_ID, postId)
            request.accumulate(USER_ID, userId)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.likePost(String.format(BEARER, token), requestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<PostResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<PostResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        posts.value = t.body()?.post
                        success.value = true
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_email_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    loading.value = View.GONE
                }
            })
    }

    /**
     * Method to register user.
     */
    fun login(userName: String, password: String) {
        loading.value = View.VISIBLE

        val request = JSONObject()
        try {
            request.accumulate(EMAIL, userName)
            request.accumulate(PASSWORD, password)
            Timber.d(request.toString());
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.login(requestBody).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<LoginResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<LoginResponse>) {
                    Timber.e("%d", t.code())
                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        Utils.savePreferences(
                            context,
                            context.getString(R.string.token),
                            t.body()?.success?.token!!
                        )
                        /* Utils.savePreferences(
                             context,
                             context.getString(R.string.user),
                             t.body()?.success?.name!!
                         )*/
                        success.value = true
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_login_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e.toString())
                    loading.value = View.GONE
                }
            })
    }


    /**
     * Method to get  all video posts.
     */
    fun getVideoPost() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)
        apiService.getVideoPost(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<JsonObject>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<JsonObject>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        /* posts.value = t.body()?.posts
                         success.value = true*/
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_email_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    loading.value = View.GONE
                }
            })
    }

    /**
     * Method to get all the friends list.
     */
    fun getFriends(userId: Int) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(USER_ID, userId)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.getFriends(String.format(BEARER, token), requestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<FriendsResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<FriendsResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        friends.value = t.body()?.friends
                        success.value = true
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_email_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    loading.value = View.GONE
                }
            })
    }

    /**
     * Method to unFriend.
     */
    fun unFriend(userId: Int) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(USER_ID, userId)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.unFriend(String.format(BEARER, token), requestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<UnFriendResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<UnFriendResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        /*friends.value = t.body()?.friends
                        success.value = true*/
                        val friendList = friends.value
                        friends.value =
                            friendList?.filter { friend -> friend.id != t.body()?.requested_user?.id }
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_email_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    loading.value = View.GONE
                }
            })
    }

    /**
     * Method to create a new post.
     */
    fun addNewPost(status: String) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(POST, status)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.addNewPost(String.format(BEARER, token), requestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<NewPostResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<NewPostResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        var post = ArrayList<Post>(posts.value)
                        post.add(0, t.body()?.post!!)
                        posts.value = post
                        success.value = true
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_email_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    loading.value = View.GONE
                }
            })
    }
}