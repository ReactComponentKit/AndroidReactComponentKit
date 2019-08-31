@file:Suppress("UNCHECKED_CAST")

package com.github.skyfe79.android.reactcomponentkit.eventbus

import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception
import org.greenrobot.eventbus.EventBus as GreenRobotEventBus

interface EventType

internal data class Notification(val name: String, val sender: Any, val userInfo: Map<String, Any>)

/**
 * token
 *  - null: Broadcast event
 *  - empty: ignore eventbus
 *  - a valid token: post and receive events between eventbus which has same token
 */
@Deprecated("Do not use EventBus. It will be removed on the next version")
class EventBus<T: EventType>(val token: Token? = null) {

    private companion object {
        val eventBus: GreenRobotEventBus = GreenRobotEventBus()
    }

    private var eventHandler: ((T) -> Unit)? = null

    val isAlive: Boolean
        get() = eventBus.isRegistered(this)


    init {
        start()
    }

    fun start() {
        // ignore empty token
        if (token != null && token == Token.empty) {
            return
        }

        if (!eventBus.isRegistered(this)) {
            eventBus.register(this)
        }
    }

    fun stop() {
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this)
        }
    }

    fun post(event: T) {
        var userInfo = mutableMapOf<String, Any>("event" to event)
        if (token != null) {
            userInfo["token"] = this.token
        }

        if (eventBus.isRegistered(this)) {
            event::class.qualifiedName?.let { eventName ->
                eventBus.post(Notification(name = eventName, sender = this@EventBus, userInfo = userInfo))
            }
        }
    }

    fun on(event: (T) -> Unit) {
        eventHandler = event
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    internal fun processNotification(notification: Notification) {
        if (!isAlive) return

        val sender = notification.sender as? EventBus<*>
        if (sender == null || sender === this) return

        val userInfo = notification.userInfo
        val token = userInfo["token"] as? Token
        val event = userInfo["event"] as? T ?: return

        if (token != null) {
            if (token == this@EventBus.token) {
                try {
                    eventHandler?.let {
                        it(event)
                    }
                } catch (_: Exception) {
                    //ignore it
                }
            }
        } else {
            // broadcast if token is null
            try {
                eventHandler?.let {
                    it(event)
                }
            } catch (_: Exception) {
                //ignore it
            }
        }
    }
}