package com.app.tmdb.extensions

import android.os.Handler
import android.os.Looper

fun addDelay(runBlock: () -> Unit, timeInMillis: Long) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(runBlock),timeInMillis)
}