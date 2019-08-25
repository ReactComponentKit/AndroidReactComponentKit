package com.github.skyfe79.android.reactcomponentkit.viewmodel

import android.app.Application
import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.ComponentNewStateEvent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.redux.ViewModelType

abstract class RootViewModelType<S: State>(application: Application): ViewModelType<S>(application) {
    val token: Token = Token()
    private val newStateEventBus: EventBus<ComponentNewStateEvent> = EventBus(token)
    private val dispatchEventBus: EventBus<ComponentDispatchEvent> = EventBus(token)
    private var previousState: S? = null

    init {
        dispatchEventBus.on { event ->
            when(event) {
                is ComponentDispatchEvent.Dispatch -> this.dispatch(event.action)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun propagate(state: State) {
        val someState = state as? S
        if (previousState != someState) {
            someState?.let {
                newStateEventBus.post(ComponentNewStateEvent.On(it))
            }
        }
        previousState = someState
    }
}