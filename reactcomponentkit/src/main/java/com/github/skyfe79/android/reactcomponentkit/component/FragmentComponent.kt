package com.github.skyfe79.android.reactcomponentkit.component

import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.ComponentNewStateEvent
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token

class FragmentComponent(override var token: Token, override var receiveState: Boolean) : ReactComponent {

    override var newStateEventBus: EventBus<ComponentNewStateEvent>?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var dispatchEventBus: EventBus<ComponentDispatchEvent>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}



}