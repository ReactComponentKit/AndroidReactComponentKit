package com.github.skyfe79.android.reactcomponentkit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.github.skyfe79.android.reactcomponentkit.RCK
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.*
import com.github.skyfe79.android.reactcomponentkit.util.runOnUiThread
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.locks.ReentrantLock

abstract class RCKViewModel<S: State>(application: Application): AndroidViewModel(application) {
    val token: Token = Token()

    private val rx_action: BehaviorRelay<Action> = BehaviorRelay.createDefault(
        VoidAction
    )
    private val store = Store<S>()
    private val disposables = CompositeDisposable()
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
        val disposable = rx_action
            .subscribeOn(Schedulers.single())
            .filter { action ->
                action !is VoidAction
            }
            .flatMap { action ->
                store.dispatch(action).toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext {
                store.doAfterEffects()
            }
            .subscribe { newState ->
                if (newState.error != null) {
                    newState.error?.let {
                        on(it)
                    }
                } else {
                    on(newState)
                }
            }

        disposables.add(disposable)
    }

    abstract fun setupStore()

    fun dispatch(action: Action) {
        rx_action.accept(action)
    }

    open fun on(error: Error) = Unit

    abstract fun on(newState: S)

    fun initStore(block: RCKViewModel<S>.(Store<S>) -> Unit) {
        RCK.registerViewModel(token, this)
        block(this.store)
    }

    @Suppress("UNCHECKED_CAST")
    fun setState(block: RCKViewModel<S>.(S) -> S): S {
        writeLock.lock()
        try {
            val newState = block(this.store.state.copyState() as S)
            runOnUiThread {
                this.on(newState)
            }
            return newState
        } finally {
            writeLock.unlock()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> withState(block: RCKViewModel<S>.(S) -> R): R {
        readLock.lock()
        try {
            return block(this.store.state.copyState() as S)
        } finally {
            readLock.unlock()
        }
    }

    fun <A: Action> asyncReducer(state: S, action: A, block: RCKViewModel<S>.(A) -> Observable<S>): S {
        asyncFlow(block)(state, action)
        return state
    }

    fun <A: Action> asyncFlow(block: RCKViewModel<S>.(A) -> Observable<S>): Reducer<S, A> {
        return { _: S, action: A ->
            block(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = { newState ->
                        writeLock.lock()
                        this.store.state = newState
                        on(newState)
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

//    @Suppress("UNCHECKED_CAST")
//    fun <A: Action> asyncEffect(action: A, block: RCKViewModel<S>.(A) -> Observable<S>) {
//        block(action)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onNext = { newState ->
//                    writeLock.lock()
//                    this.store.state = newState
//                    on(newState)
//                    writeLock.unlock()
//                },
//                onError = { error ->
//                    on(Error(error, action))
//                }
//            )
//            .addTo(disposables)
//    }
}