package com.github.skyfe79.android.reactcomponentkit.component

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.ComponentNewStateEvent
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

/**
 * Use this component for Activity Root Layout
 */
abstract class LayoutComponent<in T> : AnkoComponent<T>, ReactComponent {

    override var token: Token
    override var receiveState: Boolean
    override var newStateEventBus: EventBus<ComponentNewStateEvent>?
    override var dispatchEventBus: EventBus<ComponentDispatchEvent>

    constructor(token: Token, receiveState: Boolean) {
        this.token = token
        this.receiveState = receiveState
        this.newStateEventBus = if (receiveState) EventBus(token) else null
        this.dispatchEventBus = EventBus(token)

        newStateEventBus?.let {
            it.on { event ->
                when (event) {
                    is ComponentNewStateEvent.On -> on(event.state)
                }
            }
        }
    }

    abstract fun on(state: State)
}