package com.ravirawal.statement.util

import android.util.Log
import com.ravirawal.statement.BuildConfig

object Logger {

    fun e(tag: String? = null, message: String? = null, e: Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
            Log.e(tag.default(), message, e)
        }
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG)
            Log.i(tag, message)
    }

}