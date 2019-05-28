package com.github.skyfe79.android.reactcomponentkit.util

/**
 * Created by burt on 2016. 6. 9..
 */

import android.os.Handler
import android.os.Message

fun Handler.post(action: () -> Unit): Boolean = post(Runnable(action))
fun Handler.postAtFrontOfQueue(action: () -> Unit): Boolean = postAtFrontOfQueue(Runnable(action))
fun Handler.postAtTime(timeAtMillis: Long, action: () -> Unit): Boolean = postAtTime(Runnable(action), timeAtMillis)
fun Handler.postDelayed(delayTimeMillis: Long, action: () -> Unit): Boolean = postDelayed(Runnable(action), delayTimeMillis)
fun handler(handleMessage: (Message) -> Boolean): Handler {
    return Handler { msg -> handleMessage(msg) }
}


/***************************************************************************************************
fun ex() {

val h = handler { msg -> "do something"; true }
h.post {
println("Hello")
}


h.postAtFrontOfQueue {
}

h.postAtTime(1000L, {
})


h.postAtTime(1000L) {
}


h.postDelayed(1000L) {
}
}
 **************************************************************************************************/