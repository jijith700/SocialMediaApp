package com.sma.liveler.interfaces

import com.sma.liveler.vo.Friend

interface OnClickFriendListener {

    fun onRemove(userId: Int) {

    }

    fun onAdd(userId: Int) {

    }

    fun onClick(friend: Friend) {

    }
}