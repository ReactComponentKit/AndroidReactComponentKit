package com.github.skyfe79.android.library.app.examples.counter

import android.app.Application
import android.util.Log
import com.github.skyfe79.android.library.app.examples.counter.action.AsyncIncreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.AddEmojiAction
import com.github.skyfe79.android.reactcomponentkit.redux.*
import com.github.skyfe79.android.reactcomponentkit.rx.execute
import com.github.skyfe79.android.reactcomponentkit.util.runOnUiThread
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception

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
    val asyncCount: Output<Async<Int>> = Output(Async.Uninitialized)

    fun asyncIncrease(action: AsyncIncreaseAction): Observable<CounterState> {
        return Single.create<CounterState> { emitter ->
            Thread.sleep(1000L)
            withState {
                emitter.onSuccess(copy(count = count + action.payload))
            }
        }.toObservable()
    }

    fun increasement(payload: Int) = setState {
        copy(count = count + payload)
    }

    override fun setupStore() {
        initStore { store ->
            store.initialState(CounterState(0))

            store.flow<AsyncIncreaseAction>(
                { _, _ ->
                    setState { copy(asyncCount = Async.Loading) }
                },
                { state, action ->
                    state.copy(count = state.count + action.payload)
                },
                awaitFlow(::asyncIncrease),
                asyncFlow { action ->
                    Single.create<CounterState> { emitter ->
                        Thread.sleep(2000L)
                        withState {
                            emitter.onSuccess(copy(count = count + action.payload, asyncCount = Async.Success(count + action.payload)))
                        }
                    }.toObservable()
                }
            )

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
        asyncCount.accept(newState.asyncCount)
    }

    override fun on(error: Error) {
        Log.d("Error", error.error.message)
    }
}