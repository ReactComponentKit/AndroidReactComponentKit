package com.github.skyfe79.android.library.app.redux

import com.github.skyfe79.android.library.app.MainRoute
import com.github.skyfe79.android.library.app.MainState
import com.github.skyfe79.android.library.app.MainViewModel
import com.github.skyfe79.android.library.app.action.*
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun MainViewModel.routeReducer(action: Action): MainState = setState { state ->
    when (action) {
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

fun MainViewModel.routeClickCounterExampleButtonAction(state: MainState) = state.copy(route = MainRoute.CounterExample)

fun MainViewModel.routeClickCounterExample2ButtonAction(state: MainState) = state.copy(route = MainRoute.CounterExample2)

fun MainViewModel.routeClickRecyclerViewExampleButtonAction(state: MainState) = state.copy(route = MainRoute.RecyclerViewExample)

fun MainViewModel.routeClickEmojiExampleButtonAction(state: MainState) = state.copy(route = MainRoute.EmojiCollectionExample)

fun MainViewModel.routeClickCollectionViewExampleButtonAction(state: MainState) = state.copy(route = MainRoute.CollectionViewExample)


