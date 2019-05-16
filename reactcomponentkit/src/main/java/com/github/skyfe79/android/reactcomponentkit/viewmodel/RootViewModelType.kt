package com.github.skyfe79.android.reactcomponentkit.viewmodel

import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.ComponentNewStateEvent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.redux.ViewModelType

abstract class RootViewModelType<S: State>: ViewModelType<S>() {

    val token: Token = Token()
    val eventBus: EventBus<ComponentNewStateEvent> = EventBus(token)
    private val dispatchEventBus: EventBus<ComponentDispatchEvent> = EventBus(token)

    init {
        dispatchEventBus.on { event ->
            when(event) {
                is ComponentDispatchEvent.dispatch -> this.dispatch(event.action)
            }
        }
    }

    fun propagate(state: State) {
        val someState = state as? S
        someState?.let {
            if (someState != this.store.state) {
                eventBus.post(ComponentNewStateEvent.on(state))
            }
        }
    }
}