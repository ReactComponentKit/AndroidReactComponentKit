package com.github.skyfe79.android.reactcomponentkit

import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State

interface StateSubscriber {
    fun on(state: State) = Unit
}

interface ReactComponent: StateSubscriber {
    var token: Token
}