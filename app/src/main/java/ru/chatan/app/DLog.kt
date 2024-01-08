package ru.chatan.app

import android.util.Log
import ru.efremovkirill.ktorhandler.BuildConfig

object DLog {

    fun d(tag: String, log: String) {
        if (BuildConfig.DEBUG)
            Log.d(tag, log)
    }

    fun e(tag: String, log: String) {
        if (BuildConfig.DEBUG)
            Log.e(tag, log)
    }

}