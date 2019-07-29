package com.github.skyfe79.android.reactcomponentkit.redux

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class AndroidViewModelType<S: State>(application: Application): AndroidViewModel(application) {
    private val rx_action: BehaviorRelay<Action> = BehaviorRelay.createDefault(VoidAction)
    private val rx_state: BehaviorRelay<S?> = BehaviorRelay.create()

    val store = Store<S>()
    val disposables = CompositeDisposable()
    private var nextAction: Action? = null
    private var applyNewState: Boolean = false
    private var actionQueue: Queue<Pair<Action, Boolean>> = Queue()

    init {
        setupRxStream()
        setupStore()
    }

    override fun onCleared() {
        clear()
        super.onCleared()
    }


    fun clear() {
        disposables.dispose()
        store.deinitialize()
    }

    private fun setupRxStream() {
        val disposable1 = rx_action
            .filter { action ->
                action !is VoidAction
            }
            .map { action ->
                beforeDispatch(action)
            }
            .flatMap { action ->
                store.dispatch(action).toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { state ->
                state as? S
            }
            .subscribe { newState ->
                rx_state.accept(newState)
            }

        disposables.add(disposable1)

        val disposable2 = rx_state
            .subscribe { newState ->
                if (newState == null) return@subscribe
                if (newState.error != null) {
                    newState.error?.let {
                        on(it)
                    }
                } else {
                    val actionItem = actionQueue.dequeue()
                    if (actionItem != null) {
                        val (nextAction, apply) = actionItem
                        if (apply) {
                            on(newState)
                        }
                        rx_action.accept(nextAction)
                    } else {
                        on(newState)
                    }
                }
            }
        disposables.add(disposable2)
    }

    abstract fun setupStore()

    fun dispatch(action: Action) {
        if (actionQueue.isEmpty) {
            rx_action.accept(action)
        } else {
            actionQueue.enqueue(Pair(action, true))
        }
    }

    fun nextDispatch(action: Action, applyNewState: Boolean = false) {
        if (actionQueue.isEmpty) {
            rx_action.accept(action)
        } else {
            actionQueue.enqueue(Pair(action, applyNewState))
        }
    }

    open fun beforeDispatch(action: Action): Action = action
    open fun on(error: Error) = Unit

    abstract fun on(newState: S)
}