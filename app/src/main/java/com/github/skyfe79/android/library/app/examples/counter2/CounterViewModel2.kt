package com.github.skyfe79.android.library.app.examples.counter

import android.app.Application
import com.github.skyfe79.android.library.app.examples.counter.redux.countReducer
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType

class CounterViewModel2(application: Application): RootViewModelType<CounterState>(application) {

    val count: Output<Int> = Output(0)

    override fun setupStore() {
        initStore { store ->
            store.set(
                initialState = CounterState(0),
                reducers = arrayOf(::countReducer)
            )
        }
    }

    override fun on(newState: CounterState) {
        count.accept(newState.count)
    }
}