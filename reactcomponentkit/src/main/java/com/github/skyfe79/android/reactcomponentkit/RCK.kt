package com.github.skyfe79.android.reactcomponentkit

import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel
import com.github.skyfe79.android.reactcomponentkit.redux.State
import java.lang.ref.WeakReference

internal object RCK {

    private val map: MutableMap<Token, WeakReference<*>> = mutableMapOf()

    internal fun <S: State> registerViewModel(token: Token, viewModel: RCKViewModel<S>) {
        this.map[token] = WeakReference(viewModel)
    }

    internal fun unregisterViewModel(token: Token) {
        this.map.remove(token)
    }

    @Suppress("UNCHECKED_CAST")
    internal fun viewModel(token: Token): RCKViewModel<*>? {
        val weakViewModel = map[token]
        return weakViewModel?.get() as? RCKViewModel<*>
    }

}