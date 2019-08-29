package com.github.skyfe79.android.library.app.examples.counter

import android.app.Application
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.redux.*
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel
import io.reactivex.Single

data class CounterState(
    val count: Int,
    val asyncCount: Async<Int> = Async.Uninitialized
): State() {
    override fun copyState(): CounterState {
        return this.copy()
    }
}

class CounterViewModel(application: Application): RCKViewModel<CounterState>(application) {

    val count: Output<Int> = Output(0)

    fun showLoading() = setState { state ->
        state.copy(asyncCount = Async.Loading)
    }

    fun asyncIncrease(state: CounterState, action: IncreaseAction) = asyncReducer(state, action) {
        Single.create<CounterState> { emitter ->
            Thread.sleep(1000L)
            withState { state ->
                emitter.onSuccess(state.copy(count = state.count + action.payload))
            }
        }.toObservable()
    }

    override fun setupStore() {
        initStore { store ->
            store.initialState(CounterState(0))

            store.flow<IncreaseAction>(
                { state, action ->
                    state.copy(count = state.count + 3)
                },
                ::asyncIncrease,
                asyncFlow { action ->
                    Single.create<CounterState> { emitter ->
                        Thread.sleep(5000L)
                        withState { state ->
                            emitter.onSuccess(state.copy(count = state.count + action.payload))
                        }
                    }.toObservable()
                }
            )

            store.flow<DecreaseAction>({ state, action ->
                state.copy(count = state.count - action.payload)
            })
        }
    }

    override fun on(newState: CounterState) {
        count.accept(newState.count)
    }
}