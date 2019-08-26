package com.github.skyfe79.android.reactcomponentkit

import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.RCKViewModel
import com.github.skyfe79.android.reactcomponentkit.redux.State
import java.lang.ref.WeakReference

internal object RCK {

    private val map: MutableMap<Token, WeakReference<*>> = mutableMapOf()

    internal fun <S: State> registerViewModel(token: Token, viewModel: RCKViewModel<S>) {
        this.map[token] = WeakReference(viewModel)
    }

    internal fun <S: State> unregisterViewModel(token: Token) {
        this.map.remove(token)
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <S: State> viewModel(token: Token): RCKViewModel<S>? {
        val weakViewModel = map[token]
        return weakViewModel?.get() as? RCKViewModel<S>
    }

}