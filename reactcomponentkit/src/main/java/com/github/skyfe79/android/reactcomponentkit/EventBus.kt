package com.github.skyfe79.android.reactcomponentkit

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.EventBus as GreenRobotEventBus
import java.util.*
import kotlin.collections.HashMap

data class Token(val token: String = UUID.randomUUID().toString()) {
    companion object {
        val empty: Token = Token("")
    }
}


interface EventType

data class Notification(val name: String, val userInfo: HashMap<String, Any>? = null)

class EventBus<T: EventType> {

    val eventBus: GreenRobotEventBus = GreenRobotEventBus()

    fun start() {
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this)
        }
    }

    fun stop() {
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this)
        }
    }

    fun post(T: EventType) {
        if (eventBus.isRegistered(this)) {
            eventBus.post(Notification(name = "${T::class.java.name + "." + T.toString()}"))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(noti: Notification) {
        println(noti.name)
    }
}