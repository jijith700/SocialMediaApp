package com.sma.liveler.interfaces

import com.sma.liveler.vo.AllUsers

interface OnAllUsersListener {

    fun onClickAddFriend(userId: Int) {

    }

    fun onClickItem(allUsers: AllUsers) {

    }
}