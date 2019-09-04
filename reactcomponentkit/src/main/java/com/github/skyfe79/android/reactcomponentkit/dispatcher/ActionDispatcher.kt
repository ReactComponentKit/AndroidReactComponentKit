package com.github.skyfe79.android.reactcomponentkit.dispatcher

import com.github.skyfe79.android.reactcomponentkit.RCK
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import com.github.skyfe79.android.reactcomponentkit.redux.Action

/**
 * Dispatch actions where to has same token
 * It is used among components which has root view models.
 */
class ActionDispatcher(private val token: Token) {
    /**
     * dispatch action where to has same token.
     */
    fun dispatch(action: Action) {
        val viewModel = RCK.viewModel(token)
        viewModel.let { it?.dispatch(action) }
    }
}