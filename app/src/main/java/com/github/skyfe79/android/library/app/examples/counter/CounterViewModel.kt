package com.github.skyfe79.android.library.app.examples.counter

import com.github.skyfe79.android.library.app.examples.counter.redux.countReducer
import com.github.skyfe79.android.reactcomponentkit.redux.*
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType

data class CounterState(val count: Int): State()

class CounterViewModel: RootViewModelType<CounterState>() {

    val count: Output<Int> = Output(0)

    override fun setupStore() {
        store.set(
            initialState = CounterState(0),
            reducers = arrayOf(::countReducer)
        )
    }

    override fun on(newState: CounterState) {
        count.accept(newState.count)
        propagate(newState)
    }
}