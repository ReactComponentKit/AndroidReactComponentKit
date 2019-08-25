package com.github.skyfe79.android.library.app.redux

import com.github.skyfe79.android.library.app.MainRoute
import com.github.skyfe79.android.library.app.MainState
import com.github.skyfe79.android.library.app.MainViewModel
import com.github.skyfe79.android.library.app.action.*
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun MainViewModel.routeReducer(state: MainState, action: Action): MainState {
    return when (action) {
        is ClickCounterExampleButtonAction -> {
            val mutatedState = state.copy(route = MainRoute.CounterExample)
            mutatedState
        }
        is ClickCounterExample2ButtonAction -> {
            val mutatedState = state.copy(route = MainRoute.CounterExample2)
            mutatedState
        }
        is ClickRecyclerViewExampleButtonAction -> {
            val mutatedState = state.copy(route = MainRoute.RecyclerViewExample)
            mutatedState
        }
        is ClickEmojiExampleButtonAction -> {
            val mutatedState = state.copy(route = MainRoute.EmojiCollectionExample)
            mutatedState
        }
        is ClickCollectionViewExampleButtonAction -> {
            val mutatedState = state.copy(route = MainRoute.CollectionViewExample)
            mutatedState
        }
        else -> state
    }
}