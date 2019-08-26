package com.github.skyfe79.android.reactcomponentkit.component

import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.redux.RCKViewModel
import org.jetbrains.anko.AnkoComponent
import java.lang.ref.WeakReference

/**
 * Use this component for Activity Root Layout
 */
abstract class LayoutComponent<in T> : AnkoComponent<T>, ReactComponent {

    override var token: Token

    constructor(token: Token) {
        this.token = token
    }

    abstract fun on(state: State)
}