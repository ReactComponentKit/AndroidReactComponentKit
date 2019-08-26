package com.github.skyfe79.android.library.app.examples.counter

import android.app.Application
import com.github.skyfe79.android.library.app.examples.counter.redux.countReducer
import com.github.skyfe79.android.reactcomponentkit.redux.*

data class CounterState(val count: Int): State()

class CounterViewModel(application: Application): RCKViewModel<CounterState>(application) {

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