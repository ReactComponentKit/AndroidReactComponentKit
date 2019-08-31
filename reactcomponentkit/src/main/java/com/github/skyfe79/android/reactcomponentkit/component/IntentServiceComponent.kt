package com.github.skyfe79.android.reactcomponentkit.component

import android.app.IntentService
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State

abstract class IntentServiceComponent(token: Token, name: String): IntentService(name), ReactComponent {
    override var token: Token = token

    init {
        this.onInit()
    }

    open fun onInit() = Unit

    /**
     * It is called when the component is standalone view component.
     */
    override fun on(state: State) = Unit
}