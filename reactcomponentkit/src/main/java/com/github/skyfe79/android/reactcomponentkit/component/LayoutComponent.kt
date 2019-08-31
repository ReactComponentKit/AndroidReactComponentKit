package com.github.skyfe79.android.reactcomponentkit.component

import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.AnkoComponent

/**
 * Use this component for Activity Root Layout
 */
abstract class LayoutComponent<in T> : AnkoComponent<T>, ReactComponent {

    override var token: Token

    constructor(token: Token) {
        this.token = token
        this.onInit()
    }

    open fun onInit() = Unit

    abstract override fun on(state: State)
}