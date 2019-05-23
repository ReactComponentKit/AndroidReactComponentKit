package com.github.skyfe79.android.library.app.examples.counter

import com.github.skyfe79.android.library.app.examples.counter.redux.countReducer
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType

class CounterViewModel2: RootViewModelType<CounterState>() {

    val count: Output<Int> = Output(0)

    override fun setupStore() {
        store.set(
            initialState = CounterState(0),
            middlewares = arrayOf(),
            reducers = arrayOf(::countReducer),
            postwares = arrayOf()
        )
    }

    override fun on(newState: CounterState) {
        count.accept(newState.count)
    }
}