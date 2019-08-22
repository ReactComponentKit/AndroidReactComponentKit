package com.github.skyfe79.android.reactcomponentkit.redux

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class Store<S: State> {

    lateinit var state: S
        private set
    private lateinit var middlewares: Array<Middleware>
    private lateinit var reducers: Array<Reducer>
    private lateinit var postwares: Array<Postware>
    private lateinit var afters: Array<After<S>>
    private val disposables = CompositeDisposable()

    companion object {
        init {
            RxJavaPlugins.setErrorHandler {
            }
        }
    }

    fun set(
        initialState: S,
        middlewares: Array<Middleware> = emptyArray(),
        reducers: Array<Reducer> = emptyArray(),
        postwares: Array<Postware> = emptyArray(),
        afters: Array<After<S>> = emptyArray()) {
        this.state = initialState
        this.middlewares = middlewares
        this.reducers = reducers
        this.postwares = postwares
        this.afters = afters
    }

    fun deinitialize() {
        middlewares = emptyArray()
        reducers = emptyArray()
        postwares = emptyArray()
        afters = emptyArray()
        disposables.clear()
    }

    /**
     * Do something after dispatching new state.
     * For example, reset route on Android
     */
    internal fun doAfters() {
        var mutatedState = state
        afters.forEach {
            mutatedState = it(mutatedState)
        }
        state = mutatedState
    }

    @Suppress("UNCHECKED_CAST")
    fun dispatch(action: Action): Single<State> {
        return Single.create { single ->

            // reset error
            this@Store.state.error = null

            val disposable = middleware(this@Store.state, action)
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .flatMap { middlewareState ->
                    reduce(middlewareState, action)
                }
                .flatMap { reducedState ->
                    postware(reducedState, action)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = { newState ->
                        (newState as? S)?.let {
                            this@Store.state = it
                        }
                        single.onSuccess(this@Store.state)
                    },
                    onError = { error ->
                        this@Store.state.error = Error(error, action)
                        single.onSuccess(this@Store.state)
                    }
                )
            disposables.add(disposable)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun middleware(state: State, action: Action): Observable<State> {
        if (middlewares.isEmpty()) return Observable.just(state)

        return Single.create<State> { single ->
            val disposable = middlewares.toObservable()
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .flatMap { m ->
                    m(this@Store.state, action)
                }
                .doOnNext { modifiedState ->
                    (modifiedState as? S)?.let {
                        this@Store.state = it
                    }
                }
                .reduce { _: State, nextState: State ->
                    nextState
                }
                .subscribeBy(
                    onSuccess = { finalState: State ->
                        single.onSuccess(finalState)
                    },
                    onError = { error ->
                        this@Store.state.error = Error(error, action)
                        single.onSuccess(this@Store.state)
                    }
                )
            disposables.add(disposable)
        }.toObservable()
    }

    @Suppress("UNCHECKED_CAST")
    private fun reduce(state: State, action: Action): Observable<State> {
        if (reducers.isEmpty()) return Observable.just(state)
        if (state.error != null) return Observable.just(state)

        return Single.create<State> { single ->
            val disposable = reducers.toObservable()
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .flatMap { r ->
                    r(this@Store.state, action)
                }
                .doOnNext { modifiedState ->
                    (modifiedState as? S)?.let {
                        this@Store.state = it
                    }
                }
                .reduce { _: State, nextState: State ->
                    nextState
                }
                .subscribeBy(
                    onSuccess = { finalState ->
                        single.onSuccess(finalState)
                    },
                    onError = { error ->
                        this@Store.state.error = Error(error, action)
                        single.onSuccess(this@Store.state)
                    }
                )
            disposables.add(disposable)
        }.toObservable()
    }

    @Suppress("UNCHECKED_CAST")
    private fun postware(state: State, action: Action): Observable<State> {
        if (postwares.isEmpty()) return Observable.just(state)
        if (state.error != null) return Observable.just(state)

        return Single.create<State> { single ->
            val disposable = postwares.toObservable()
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .flatMap { p ->
                    p(this@Store.state, action)
                }
                .doOnNext { modifiedState ->
                    (modifiedState as? S)?.let {
                        this@Store.state = it
                    }
                }
                .reduce { _: State, nextState: State ->
                    nextState
                }
                .subscribeBy(
                    onSuccess = { finalState ->
                        single.onSuccess(finalState)
                    },
                    onError = { error ->
                        this@Store.state.error = Error(error, action)
                        single.onSuccess(this@Store.state)
                    }
                )
            disposables.add(disposable)
        }.toObservable()
    }
}