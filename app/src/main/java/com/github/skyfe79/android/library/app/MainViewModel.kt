package com.github.skyfe79.android.library.app

import com.github.skyfe79.android.library.app.redux.countReducer
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.Error
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType
import com.jakewharton.rxrelay2.BehaviorRelay


data class MainState(var count: Int): State()

class MainViewModel: RootViewModelType<MainState>() {

    class Output {
        val count: BehaviorRelay<Int> = BehaviorRelay.createDefault(0)
    }

    val output = Output()

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
        output.count.accept(newState.count)
        propagate(newState)
    }

    override fun on(error: Error) {
        println(error.error.localizedMessage)
    }

}