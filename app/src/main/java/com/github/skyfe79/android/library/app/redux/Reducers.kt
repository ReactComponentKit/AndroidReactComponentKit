package com.github.skyfe79.android.library.app.redux

import com.github.skyfe79.android.library.app.MainRoute
import com.github.skyfe79.android.library.app.MainState
import com.github.skyfe79.android.library.app.MainViewModel
import com.github.skyfe79.android.library.app.action.ClickCounterExample2ButtonAction
import com.github.skyfe79.android.library.app.action.ClickCounterExampleButtonAction
import com.github.skyfe79.android.library.app.action.ClickEmojiExampleButtonAction
import com.github.skyfe79.android.library.app.action.ClickRecyclerViewExampleButtonAction
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun MainViewModel.routeReducer(state: State, action: Action): Observable<State> {
    val mainState = (state as? MainState) ?: return Observable.just(state)

    return when (action) {
        is ClickCounterExampleButtonAction -> {
            val mutatedState = mainState.copy(route = MainRoute.CounterExample)
            Observable.just(mutatedState)
        }
        is ClickCounterExample2ButtonAction -> {
            val mutatedState = mainState.copy(route = MainRoute.CounterExample2)
            Observable.just(mutatedState)
        }
        is ClickRecyclerViewExampleButtonAction -> {
            val mutatedState = mainState.copy(route = MainRoute.RecyclerViewExample)
            Observable.just(mutatedState)
        }
        is ClickEmojiExampleButtonAction -> {
            val mutatedState = mainState.copy(route = MainRoute.EmojiCollectionExample)
            Observable.just(mutatedState)
        }
        else -> Observable.just(state)
    }
}