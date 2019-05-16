package com.github.skyfe79.android.library.app

import com.github.skyfe79.android.library.app.redux.countReducer
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.Error
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType


data class MainState(var count: Int): State()

class MainViewModel: RootViewModelType<MainState>() {

    override fun setupStore() {
        store.set(
            initialState = MainState(0),
            middlewares = arrayOf(),
            reducers = arrayOf(::countReducer),
            postwares = arrayOf()
        )
    }

    override fun beforeDispatch(action: Action): Action {
        return action
    }

    override fun on(newState: MainState) {
        println(newState)
    }

    override fun on(error: Error) {
        println(error.error.localizedMessage)
    }

}