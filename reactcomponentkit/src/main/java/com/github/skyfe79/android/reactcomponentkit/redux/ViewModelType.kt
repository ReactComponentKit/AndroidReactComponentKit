package com.github.skyfe79.android.reactcomponentkit.redux

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.atomic.AtomicBoolean

abstract class ViewModelType<S: State>: ViewModel() {

    private val rx_action: BehaviorRelay<Action> = BehaviorRelay.createDefault(VoidAction)
    private val rx_state: BehaviorRelay<S?> = BehaviorRelay.create()

    val store = Store<S>()
    private val disposables = CompositeDisposable()
    private var applyNewState: Boolean = false
    private var actionQueue: Queue<Pair<Action, Boolean>> = Queue()
    private var isProcessingAction: AtomicBoolean = AtomicBoolean(false)

    init {
        setupRxStream()
        setupStore()
    }

    override fun onCleared() {
        dispose()
        super.onCleared()
    }


    fun dispose() {
        disposables.dispose()
        store.deinitialize()
    }

    private fun setupRxStream() {
        val disposable1 = rx_action
            .doOnNext {
                isProcessingAction.set(true)
            }
            .filter { action ->
                if (action !is VoidAction) {
                    true
                } else {
                    isProcessingAction.set(false)
                    false
                }
            }
            .map { action ->
                beforeDispatch(action)
            }
            .filter { action ->
                if (action !is VoidAction) {
                    true
                } else {
                    isProcessingAction.set(false)
                    false
                }
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
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext {
                isProcessingAction.set(false)

                val actionItem = actionQueue.peek()
                if (actionItem != null ) {
                    val (nextAction, _) = actionItem
                    rx_action.accept(nextAction)
                    // deque actions after processing
                    actionQueue.dequeue()
                }
            }
            .doOnError {
                isProcessingAction.set(false)
                val actionItem = actionQueue.peek()
                if (actionItem != null ) {
                    actionQueue.dequeue()
                }
            }
            .subscribe { newState ->
                if (newState == null) return@subscribe
                if (newState.error != null) {
                    newState.error?.let {
                        on(it)
                        store.doAfters()
                    }
                } else {
                    val actionItem = actionQueue.peek()
                    if (actionItem != null) {
                        val (_, apply) = actionItem
                        if (apply) {
                            on(newState)
                            store.doAfters()
                        }
                    } else {
                        on(newState)
                        store.doAfters()
                    }
                }
            }
        disposables.add(disposable2)
    }

    abstract fun setupStore()

    fun dispatch(action: Action) {
        if (actionQueue.isNotEmpty) {
            if (isProcessingAction.get()) {
                actionQueue.enqueue(Pair(action, true))
            } else {
                rx_action.accept(action)
            }
        } else {
            if (isProcessingAction.get()) {
                actionQueue.enqueue(Pair(action, true))
            } else {
                rx_action.accept(action)
            }
        }
    }

    fun nextDispatch(action: Action, applyNewState: Boolean = false) {
        if (actionQueue.isNotEmpty) {
            if (isProcessingAction.get()) {
                actionQueue.enqueue(Pair(action, applyNewState))
            } else {
                rx_action.accept(action)
            }
        } else {
            if (isProcessingAction.get()) {
                actionQueue.enqueue(Pair(action, applyNewState))
            } else {
                rx_action.accept(action)
            }
        }
    }

    open fun beforeDispatch(action: Action): Action = action
    open fun on(error: Error) = Unit

    abstract fun on(newState: S)
}