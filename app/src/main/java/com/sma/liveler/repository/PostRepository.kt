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
import okhttp3.MultipartBody
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
    var videoPosts = MutableLiveData<List<Post>>()
    var friends = MutableLiveData<List<Friend>>()
    var friendsRequest = MutableLiveData<List<Request>>()
    var todaysPost = MutableLiveData<Post>()
    var user = MutableLiveData<User>()
    var bankDetails = MutableLiveData<BankDetails>()
    var notifications = MutableLiveData<List<UnreadNotification>>()
    var friendRequest = MutableLiveData<List<FriendRequest>>()
    var ads = MutableLiveData<List<Ad>>()
    var userInfo = MutableLiveData<UserInfoResponse>()
    var chatMessages = MutableLiveData<List<ChatMessage>>()
    var sendUser = MutableLiveData<User>()
    var receiverUser = MutableLiveData<User>()
    var allUsers = MutableLiveData<List<AllUsers>>()

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
//                        Log.d("success:", Gson().toJson(t.body()))
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
     * Method to get all video posts.
     */
    fun getVideoPost() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)
        apiService.getVideoPost(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<VideoPostResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<VideoPostResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        videoPosts.value = t.body()?.posts?.data
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
     * Method to get daily video post.
     */
    fun getDailyVideo() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)
        apiService.getDailyVideo(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<TodayVideoResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<TodayVideoResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        todaysPost.value = t.body()?.post
                        user.value = t.body()?.user
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
     * Method to get all the friends list.
     */
    fun getFriends() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        apiService.getFriends(String.format(BEARER, token))
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
     * Method to get all the friends list.
     */
    fun getFollowing() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        apiService.getFollowing(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<FollowingResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<FollowingResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        Timber.d("success: %s", Gson().toJson(t.body()))
                        friendsRequest.value = t.body()?.requests
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
     * Method to add Friend.
     */
    fun addFriend(userId: Int) {
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

        apiService.addFriend(String.format(BEARER, token), requestBody)
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
                        /*friends.value = t.body()?.friends
                        success.value = true*/
                        val friendList = friends.value
                        /*friends.value =
                            friendList?.filter { friend -> friend.id != t.body()?.requested_user?.id }*/
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

    /**
     * Method to add new bank account.
     */
    fun addBankAccount(
        holderName: String,
        bankName: String,
        accountNumber: String,
        branch: String,
        ifsc: String
    ) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(ACCOUNT_HOLDER_NAME, holderName)
            request.accumulate(ACCOUNT_NUMBER, accountNumber)
            request.accumulate(BANK, bankName)
            request.accumulate(BRANCH, branch)
            request.accumulate(IFSC, ifsc)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.addBankAccount(String.format(BEARER, token), requestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<BankResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<BankResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
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
     * Method to get the details of the bank account.
     */
    fun getBankDetails() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        apiService.getBankDetails(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<BankResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<BankResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        bankDetails.value = t.body()?.bankInfo?.bank_details
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
     * Method to upload video.
     */
    fun uploadMedia(status: String, isDaily: Boolean, type: String, body: MultipartBody.Part) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        apiService.uploadVideo(String.format(BEARER, token), body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<UploadResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<UploadResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        val uploadResponse = t.body()

                        addNewMediaPost(
                            status,
                            type,
                            isDaily,
                            uploadResponse?.fileName!!,
                            uploadResponse?.thumbName,
                            uploadResponse?.thumb
                        )
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
     * Method to get the details of the bank account.
     */
    fun getUserDetails() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        apiService.getUserDetails(String.format(BEARER, token))
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
                        /*bankDetails.value = t.body()?.bankInfo?.bank_details*/
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
     * Method to add ne media post.
     */
    fun addNewMediaPost(
        status: String,
        type: String,
        isDaily: Boolean,
        fileSavedName: String,
        thumbName: String,
        fileThumb: String
    ) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(STATUS_MEDIA_TEXT, status)
            request.accumulate(FILE_TYPE, type)
            request.accumulate(IS_DAILY, isDaily)
            request.accumulate(FILE_SAVED_NAME, fileSavedName)
            request.accumulate(THUMB_NAME, thumbName)
            request.accumulate(FILE_THUMB, fileThumb)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.addNewMediaPost(String.format(BEARER, token), requestBody)
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
                        if (posts.value.isNullOrEmpty()) {
                            val post = ArrayList<Post>()
                            post.add(t.body()?.post!!)
                            posts.value = post
                        } else {
                            val post = ArrayList<Post>(posts.value)
                            post.add(0, t.body()?.post!!)
                            posts.value = post
                        }
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
    fun getNotifications() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        apiService.getNotifications(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<NotificationResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<NotificationResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        Timber.d("success: %s", Gson().toJson(t.body()))
                        user.value = t.body()?.user
                        notifications.value = t.body()?.user?.unread_notifications
                        friendRequest.value = t.body()?.user?.friendRequests
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
     * Method to read all the notification list.
     */
    fun readAllNotifications() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        apiService.readAllNotifications(String.format(BEARER, token))
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
     * Method to accept friend request.
     */
    fun acceptFriendRequest(userId: Int) {
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

        apiService.acceptFriendRequest(String.format(BEARER, token), requestBody)
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
                        /*friends.value = t.body()?.friends
                        success.value = true*/
                        val friendList = friends.value
                        /*friends.value =
                            friendList?.filter { friend -> friend.id != t.body()?.requested_user?.id }*/
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
     * Method to cancel friend request.
     */
    fun cancelFriendRequest(userId: Int) {
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

        apiService.cancelFriendRequest(String.format(BEARER, token), requestBody)
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
                        /*friends.value = t.body()?.friends
                        success.value = true*/
                        val friendList = friends.value
                        /*friends.value =
                            friendList?.filter { friend -> friend.id != t.body()?.requested_user?.id }*/
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
     * Method to get all ads.
     */
    fun getAds() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)


        apiService.getAds(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<MyAdResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<MyAdResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        ads.value = t.body()?.ads
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
     * Method to post an ads.
     */
    fun getPersonalInfo() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)


        apiService.getPersonalInfo(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<UserInfoResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<UserInfoResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        userInfo.value = t.body()
                        /*friends.value = t.body()?.friends
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
     * Method to send chat message.
     */
    fun sendMessage(fromUser: Int, toUser: Int, msg: String) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(FROM_ID, fromUser)
            request.accumulate(TO_ID, toUser)
            request.accumulate(MESSAGE, msg)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.sendMessage(String.format(BEARER, token), requestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<SendMessageResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<SendMessageResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        success.value = true
                        sendUser.value = t.body()?.user
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
     * Method to send chat message.
     */
    fun getMessage(userId: Int) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(FRIEND_ID, userId)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.getMessage(String.format(BEARER, token), requestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<ReceiveMessageResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<ReceiveMessageResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        receiverUser.value = t.body()?.user
                        chatMessages.value = t.body()?.messages
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
     * Method to get all users.
     */
    fun getAllUsers() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)


        apiService.getAllUsers(String.format(BEARER, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<AllUsersResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<AllUsersResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        allUsers.value = t.body()?.users
                        /*friends.value = t.body()?.friends
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
     * Method to upload video Ad.
     */
    fun uploadMediaAd(title: String, body: MultipartBody.Part) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        apiService.uploadVideoAd(String.format(BEARER, token), body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<UploadResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<UploadResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        val uploadResponse = t.body()

                        addNewMediaAd(
                            title,
                            uploadResponse?.file!!,
                            uploadResponse.thumb
                        )
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
     * Method to add ne media post.
     */
    fun addNewMediaAd(title: String, videoUrl: String, thumbUrl: String) {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)

        val request = JSONObject()
        try {
            request.accumulate(TITLE_TEXT, title)
            request.accumulate(VIDEO_URL, videoUrl)
            request.accumulate(THUMB_URL, thumbUrl)
            Timber.d(request.toString())
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.postAd(String.format(BEARER, token), requestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<PostAdResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<PostAdResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
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