package com.github.skyfe79.android.library.app.examples.counter

import android.app.Application
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel

class CounterViewModel2(application: Application): RCKViewModel<CounterState>(application) {

    val count: Output<Int> = Output(0)

    override fun setupStore() {
        initStore { store ->
            store.initialState(CounterState(0))

            store.flow<IncreaseAction>({ state, action ->
                state.copy(count = state.count + action.payload)
            })

            store.flow<DecreaseAction>({ state, action ->
                state.copy(count = state.count - action.payload)
            })
        }

    }

    override fun on(newState: CounterState) {
        count.accept(newState.count)
    }
}