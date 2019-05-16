package com.github.skyfe79.android.library.app.redux

import com.github.skyfe79.android.library.app.MainState
import com.github.skyfe79.android.library.app.MainViewModel
import com.github.skyfe79.android.library.app.action.DecreaseAction
import com.github.skyfe79.android.library.app.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun MainViewModel.countReducer(state: State, action: Action): Observable<State> {
    val mainState = (state as? MainState) ?: return Observable.just(state)

    return when(action) {
        is IncreaseAction -> {
            val mutatedState = mainState.copy(count = mainState.count + action.payload)
            Observable.just(mutatedState)
        }
        is DecreaseAction -> {
            val mutatedState = mainState.copy(count = mainState.count - action.payload)
            Observable.just(mutatedState)
        }
        else -> {
            Observable.just(state)
        }
    }
}