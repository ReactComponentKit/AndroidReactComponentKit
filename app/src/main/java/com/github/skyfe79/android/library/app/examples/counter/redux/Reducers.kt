package com.github.skyfe79.android.library.app.examples.counter.redux

import com.github.skyfe79.android.library.app.examples.counter.CounterState
import com.github.skyfe79.android.library.app.examples.counter.CounterViewModel
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun countReducer(state: State, action: Action): Observable<State> {
    val counterState = (state as? CounterState) ?: return Observable.just(state)

    return when(action) {
        is IncreaseAction -> {
            val mutatedState = counterState.copy(count = counterState.count + action.payload)
            Observable.just(mutatedState)
        }
        is DecreaseAction -> {
            val mutatedState = counterState.copy(count = counterState.count - action.payload)
            Observable.just(mutatedState)
        }
        else -> {
            Observable.just(state)
        }
    }
}