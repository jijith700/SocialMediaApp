package com.sma.liveler.interfaces

import java.io.File

interface OnClickPostListener {

    fun onClickLike(postId: Int)

    fun onClickPost(status: String, type: String) {

    }

    fun onClickPost(status: String, type: String, file: File) {

    }
}