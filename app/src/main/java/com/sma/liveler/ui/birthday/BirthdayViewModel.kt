package com.sma.liveler.ui.birthday

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.vo.BirthDay
import com.sma.liveler.vo.Friend
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BirthdayViewModel() : ViewModel() {

    var TAG = BirthdayViewModel::class.java.simpleName

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var friends = MutableLiveData<List<Friend>>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        friends = postRepository.friends
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getFriends() {
        postRepository.getFriends()
    }

    fun acceptFriendRequest(userId: Int) {
        postRepository.acceptFriendRequest(userId)
    }

    fun cancelFriendRequest(userId: Int) {
        postRepository.cancelFriendRequest(userId)
    }

    fun getBirthDay(): ArrayList<BirthDay> {
        val month = ArrayList<String>()
        month.add("January")
        month.add("February")
        month.add("March")
        month.add("April")
        month.add("May")
        month.add("June")
        month.add("July")
        month.add("August")
        month.add("September")
        month.add("October")
        month.add("November")
        month.add("December")

        val birthDayList = ArrayList<BirthDay>()
        for (i in 0 until month.size) {
            setBirthDay(month[i], birthDayList)
        }

        return birthDayList
    }

    private fun setBirthDay(month: String, birthDayList: ArrayList<BirthDay>) {
        val birthDay = BirthDay(month, ArrayList<Friend>())
        birthDayList.add(birthDay)
    }

    fun getBirthDay(
        birthDayList: ArrayList<BirthDay>,
        friendsList: List<Friend>
    ): ArrayList<BirthDay> {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        for (friend in friendsList) {
            if (!TextUtils.isEmpty(friend.date_of_birth)) {
                val date = dateFormatter.parse(friend.date_of_birth)
                Timber.d(date.toString())
                val cal = Calendar.getInstance()
                cal.time = date
                val month = cal.get(Calendar.MONTH)
                Timber.d("month %d", month)
                birthDayList[month].items.add(friend)
            }
        }
        return birthDayList
    }
}