package com.github.skyfe79.android.reactcomponentkit.util

import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.Action

/**
 * Dispatch actions where to has same token
 * It is used among components which has root view models.
 */
class ActionDispatcher(private val token: Token) {
    private val dispatchEventBus = EventBus<ComponentDispatchEvent>(token)

    /**
     * dispatch action where to has same token.
     */
    fun dispatch(action: Action) {
        dispatchEventBus.post(ComponentDispatchEvent.Dispatch(action))
    }
}