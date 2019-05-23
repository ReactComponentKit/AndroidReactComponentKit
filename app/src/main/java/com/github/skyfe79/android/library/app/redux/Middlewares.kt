package com.github.skyfe79.android.library.app.redux

import com.github.skyfe79.android.library.app.MainRoute
import com.github.skyfe79.android.library.app.MainState
import com.github.skyfe79.android.library.app.MainViewModel
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun MainViewModel.reset(state: State, action: Action): Observable<State> {
    val mainState = (state as? MainState) ?: return Observable.just(state)

    val mutatedState = mainState.copy(route = MainRoute.None)

    return Observable.just(mutatedState)
}