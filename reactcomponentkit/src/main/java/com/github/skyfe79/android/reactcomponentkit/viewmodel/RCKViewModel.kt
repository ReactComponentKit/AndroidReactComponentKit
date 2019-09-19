package com.github.skyfe79.android.reactcomponentkit.viewmodel

import android.app.Application
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import com.github.skyfe79.android.reactcomponentkit.RCK
import com.github.skyfe79.android.reactcomponentkit.StateSubscriber
import com.github.skyfe79.android.reactcomponentkit.redux.*
import com.github.skyfe79.android.reactcomponentkit.rx.DisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.disposedBy
import com.github.skyfe79.android.reactcomponentkit.util.runOnUiThread
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.newSingleThreadContext
import java.lang.ref.WeakReference
import java.util.concurrent.locks.ReentrantLock

abstract class RCKViewModel<S: State>(application: Application): AndroidViewModel(application) {
    val token: Token = Token()

    private val rx_action: BehaviorRelay<Action> = BehaviorRelay.createDefault(
        VoidAction
    )
    private val store = Store<S>()
    private val disposables = CompositeDisposable()
    private var actionQueue: Queue<Action> = Queue()
    private val dispatchLock = ReentrantLock()
    private val writeLock = ReentrantLock()
    private var subscribers: MutableList<WeakReference<StateSubscriber>> = mutableListOf()

    init {
        this.setupRxStream()
        this.setupStore()
    }

    override fun onCleared() {
        this.dispose()
        super.onCleared()
    }

    /**
     * clean up memeries
     */
    fun dispose() {
        RCK.unregisterViewModel(token)
        actionQueue.clear()
        subscribers = mutableListOf()
        disposables.dispose()
        store.deinitialize()
    }

    private fun setupRxStream() {
        val disposable = rx_action
            .subscribeOn(Schedulers.single())
            .filter { action ->
                action !is VoidAction
            }
            .observeOn(AndroidSchedulers.mainThread())
            .filter { action ->
                store.startFlow(action) !is VoidAction
            }
            .observeOn(Schedulers.single())
            .flatMap { action ->
                store.dispatch(action).toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext {
                store.doAfterEffects()
                if (actionQueue.isNotEmpty) {
                    val nextAction = actionQueue.dequeue()
                    nextAction?.let {
                        rx_action.accept(it)
                    }
                }
            }
            .subscribe { newState ->
                if (newState.error != null) {
                    newState.error?.let {
                        on(it)
                        actionQueue.clear()
                    }
                } else {
                    dispatchStateToSubscribers(newState)
                }


            }

        disposables.add(disposable)
    }

    /**
     * register subscriber that receive the new state.
     */
    fun registerSubscriber(subscriber: StateSubscriber) {
        val weakSubscriber = WeakReference<StateSubscriber>(subscriber)
        subscribers.add(weakSubscriber)
    }

    /**
     * dispatch mutated state to subscribers
     */
    private fun dispatchStateToSubscribers(state: S) {
        dispatchLock.lock()
        try {
            on(state)
            subscribers.forEach {
                it.get()?.on(state)
            }
        } finally {
            dispatchLock.unlock()
        }
    }


    /**
     * dispatch action to the store.
     */
    fun dispatch(action: Action) {
        rx_action.accept(action)
    }

    /**
     * dispatch action on the next run loop to the store.
     */
    fun nextDispatch(action: Action) {
        if (actionQueue.isEmpty) {
            rx_action.accept(action)
        } else {
            actionQueue.enqueue(action)
        }
    }

    /**
     * Called when receive the new state from store
     */
    protected abstract fun on(newState: S)

    /**
     * Called when occured erros on the redux flow.
     */
    protected open fun on(error: Error) = Unit

    /**
     * Setup store. You should define this method and use initStore method in it.
     */
    abstract fun setupStore()

    /**
     * init store with block
     * You can set initial state and define flows for actions
     */
    protected fun initStore(block: RCKViewModel<S>.(Store<S>) -> Unit) {
        RCK.registerViewModel(token, this)
        block(this.store)
    }

    /**
     * Set state and dispatch the mutated state to subscribers
     */
    @Suppress("UNCHECKED_CAST")
    fun setState(block: S.(S) -> S): S {
        writeLock.lock()
        try {
            val state = this.store.state.copyState() as S
            val newState = state.block(state)
            this.store.state = newState
            if (Looper.myLooper() == Looper.getMainLooper()) {
                dispatchStateToSubscribers(newState)
            } else {
                runOnUiThread {
                    dispatchStateToSubscribers(newState)
                }
            }
            return newState
        } finally {
            writeLock.unlock()
        }

    }

    /**
     * Read state value
     */
    @Suppress("UNCHECKED_CAST")
    fun <R> withState(block: S.(S) -> R): R {
        val state = state()
        return state.block(state)
    }

    @Suppress("UNCHECKED_CAST")
    fun state(): S {
        return store.state.copyState() as S
    }

    /**
     * Do async job and wait until finished it.
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <A: Action> awaitFlow(asyncReducer: AsyncReducer<S, A>): Reducer<S, A> {
        return { _: S, action: A ->
            asyncReducer(action)
                .subscribeOn(Schedulers.io())
                .blockingLast(this.store.state.copyState() as S)
        }
    }

    /**
     * Do async job and return right away.
     */
    protected fun <A: Action> asyncFlow(asyncReducer: AsyncReducer<S, A>): Reducer<S, A> {
        return { _: S, action: A ->
            asyncReducer(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = { newState ->
                        writeLock.lock()
                        this.store.state = newState
                        dispatchStateToSubscribers(newState)
                        writeLock.unlock()
                    },
                    onError = { error ->
                        on(Error(error, action))
                    }
                )
                .addTo(disposables)
            null
        }
    }
}