package com.github.skyfe79.android.reactcomponentkit

import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventType
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State

sealed class ComponentNewStateEvent: EventType {
   data class On(val state: State): ComponentNewStateEvent()
}

sealed class ComponentDispatchEvent: EventType {
    data class Dispatch(val action: Action): ComponentDispatchEvent()
}

interface ReactComponent {
    var token: Token
    var receiveState: Boolean
    var newStateEventBus: EventBus<ComponentNewStateEvent>?
    var dispatchEventBus: EventBus<ComponentDispatchEvent>

    fun dispatch(action: Action) {
        dispatchEventBus.post(ComponentDispatchEvent.Dispatch(action))
    }
}