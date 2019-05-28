package com.github.skyfe79.android.reactcomponentkit.util

/**
 * Created by burt on 2016. 6. 9..
 */

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future


private val uiHandler = Handler(Looper.getMainLooper())


fun Any?.runOnUiThread(runnable: () -> Unit) {
    uiHandler.post(runnable)
}

fun Any?.runOnUiThreadAfterDelay(delay : Long = 0L, runnable: () -> Unit) {
    uiHandler.postDelayed(delay, runnable)
}

fun Any?.async(runnable: () -> Unit) {
    Thread(runnable).start()
}

fun Any?.async(executor: ExecutorService, runnable: () -> Unit): Future<out Any?> {
    return executor.submit(runnable)
}