package com.github.skyfe79.android.reactcomponentkit.component

import android.view.View
import android.view.ViewGroup
import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.ComponentNewStateEvent
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

class ViewComponent(override var token: Token, override var receiveState: Boolean): AnkoComponent<ViewGroup>, ReactComponent {

    override var newStateEventBus: EventBus<ComponentNewStateEvent>?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var dispatchEventBus: EventBus<ComponentDispatchEvent>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}


    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}