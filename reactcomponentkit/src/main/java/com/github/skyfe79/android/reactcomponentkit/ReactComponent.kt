package com.github.skyfe79.android.reactcomponentkit

import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventType
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State

sealed class ComponentNewStateEvent: EventType {
   data class on(val state: State): ComponentNewStateEvent()
}

sealed class ComponentDispatchEvent: EventType {
    data class dispatch(val action: Action): ComponentDispatchEvent()
}

interface ReactComponent {
    var token: Token
    var receiveState: Boolean
    var newStateEventBus: EventBus<ComponentNewStateEvent>?
    var dispatchEventBus: EventBus<ComponentDispatchEvent>
}

//class Mycomponent(override var token: Token, override var receiveState: Boolean) : ReactComponent {
//
//    override var newStateEventBus: EventBus<ComponentNewStateEvent>? = if (receiveState) EventBus(token) else null
//    override var dispatchEventBus: EventBus<ComponentDispatchEvent> = EventBus(token)
//
//    init {
//        newStateEventBus.on { event ->
//            when(event) {
//                is ComponentNewStateEvent.on ->
//
//            }
//        }
//    }
//}