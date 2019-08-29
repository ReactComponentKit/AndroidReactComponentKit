package com.github.skyfe79.android.reactcomponentkit.redux

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.github.skyfe79.android.reactcomponentkit.RCK
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock

abstract class RCKViewModel<S: State>(application: Application): AndroidViewModel(application) {
    val token: Token = Token()

    private val rx_action: BehaviorRelay<Action> = BehaviorRelay.createDefault(VoidAction)
    private val rx_state: BehaviorRelay<S?> = BehaviorRelay.create()


    private val store = Store<S>()
    private val disposables = CompositeDisposable()
    private var applyNewState: Boolean = false
    private var actionQueue: Queue<Pair<Action, Boolean>> = Queue()
    private var isProcessingAction: AtomicBoolean = AtomicBoolean(false)
    private var currentAction: Action = VoidAction
    private val writeLock = ReentrantLock()
    private val readLock = ReentrantLock()

    init {
        setupRxStream()
        this.setupStore()
    }

    override fun onCleared() {
        dispose()
        super.onCleared()
    }


    fun dispose() {
        RCK.unregisterViewModel<S>(token)
        disposables.dispose()
        store.deinitialize()
    }

    private fun setupRxStream() {
        val disposable1 = rx_action
            .subscribeOn(Schedulers.single())
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
                currentAction = action
                store.dispatch(action).toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
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

    fun initStore(block: RCKViewModel<S>.(Store<S>) -> Unit) {
        RCK.registerViewModel(token, this)
        block(this.store)
    }

    /*
    fun setState(block: RCKViewModel<S>.(S) -> S): S {
        writeLock.lock()
        try {
            val newState = block(this.store.state)
            this.on(newState)
            return newState
        } finally {
            writeLock.unlock()
        }
    }
    */

    fun <R> withState(block: RCKViewModel<S>.(S) -> R): R {
        readLock.lock()
        try {
            return block(this.store.state)
        } finally {
            readLock.unlock()
        }
    }
}