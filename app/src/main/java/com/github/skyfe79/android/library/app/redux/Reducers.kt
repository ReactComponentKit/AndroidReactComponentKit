package com.github.skyfe79.android.library.app.redux

import com.github.skyfe79.android.library.app.MainRoute
import com.github.skyfe79.android.library.app.MainState
import com.github.skyfe79.android.library.app.MainViewModel
import com.github.skyfe79.android.library.app.action.*
import com.github.skyfe79.android.reactcomponentkit.redux.Action


fun MainViewModel.routeToCounterExample(state: MainState, action: ClickCounterExampleButtonAction): MainState {
    return state.copy(route = MainRoute.CounterExample)
}

fun MainViewModel.routeToCounterExample2(state: MainState, action: ClickCounterExample2ButtonAction) = state.copy(route = MainRoute.CounterExample2)

fun MainViewModel.routeToRecyclerViewExample(state: MainState, action: ClickRecyclerViewExampleButtonAction) = state.copy(route = MainRoute.RecyclerViewExample)

fun MainViewModel.routeToEmojiExample(state: MainState, action: ClickEmojiExampleButtonAction) = state.copy(route = MainRoute.EmojiCollectionExample)

fun MainViewModel.routeToCollectionViewExample(state: MainState, action: ClickCollectionViewExampleButtonAction) = state.copy(route = MainRoute.CollectionViewExample)


