package com.github.skyfe79.android.library.app.examples.counter.redux

import com.github.skyfe79.android.library.app.examples.counter.CounterState
import com.github.skyfe79.android.library.app.examples.counter.CounterViewModel
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun countReducer(state: CounterState, action: Action): CounterState {
    return when(action) {
        is IncreaseAction -> {
            val mutatedState = state.copy(count = state.count + action.payload)
            mutatedState
        }
        is DecreaseAction -> {
            val mutatedState = state.copy(count = state.count - action.payload)
            mutatedState
        }
        else -> state
    }
}