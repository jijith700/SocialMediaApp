package com.sma.liveler.interfaces

import java.io.File

interface OnClickMediaListener {

    fun onClickImage()

    fun onClickVideo()

    fun onClickPreview(status: String, fileName: File, type: String)
}