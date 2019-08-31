package com.github.skyfe79.android.reactcomponentkit.dispatcher

import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventType
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State

@Deprecated("Do not use ComponentNewStateEvent. It will be removed on the next version")
sealed class ComponentNewStateEvent: EventType {
    data class On(val state: State): ComponentNewStateEvent()
}

@Deprecated("Do not use ComponentDispatchEvent. It will be removed on the next version")
sealed class ComponentDispatchEvent: EventType {
    data class Dispatch(val action: Action): ComponentDispatchEvent()
}

/**
 * Dispatch actions where to has same token
 * It is used among components which has root view models.
 */
@Deprecated("Do not use ActionDispatcher. It will be removed on the next version")
class ActionDispatcher(private val token: Token) {
    private val dispatchEventBus = EventBus<ComponentDispatchEvent>(token)

    /**
     * dispatch action where to has same token.
     */
    fun dispatch(action: Action) {
        dispatchEventBus.post(
            ComponentDispatchEvent.Dispatch(
                action
            )
        )
    }
}