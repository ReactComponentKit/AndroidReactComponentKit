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
    private lateinit var middlewares: Array<Middleware<S>>
    private lateinit var reducers: Array<Reducer<S>>
    private lateinit var postwares: Array<Postware<S>>
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
        middlewares: Array<Middleware<S>> = emptyArray(),
        reducers: Array<Reducer<S>> = emptyArray(),
        postwares: Array<Postware<S>> = emptyArray(),
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

    fun dispatch(action: Action): Single<S> {
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
                        this@Store.state = newState
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

    private fun middleware(state: S, action: Action): Observable<S> {
        if (middlewares.isEmpty()) return Observable.just(state)

        return Single.create<S> { single ->
            val disposable = middlewares.toObservable()
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .map { m ->
                    m(this@Store.state, action)
                }
                .doOnNext { modifiedState ->
                    this@Store.state = modifiedState
                }
                .reduce { _: S, nextState: S ->
                    nextState
                }
                .subscribeBy(
                    onSuccess = { finalState: S ->
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

    private fun reduce(state: S, action: Action): Observable<S> {
        if (reducers.isEmpty()) return Observable.just(state)
        if (state.error != null) return Observable.just(state)

        return Single.create<S> { single ->
            val disposable = reducers.toObservable()
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .map { r ->
                    r(this@Store.state, action)
                }
                .doOnNext { modifiedState ->
                    this@Store.state = modifiedState
                }
                .reduce { _: S, nextState: S ->
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

    private fun postware(state: S, action: Action): Observable<S> {
        if (postwares.isEmpty()) return Observable.just(state)
        if (state.error != null) return Observable.just(state)

        return Single.create<S> { single ->
            val disposable = postwares.toObservable()
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .map { p ->
                    p(this@Store.state, action)
                }
                .doOnNext { modifiedState ->
                    this@Store.state = modifiedState
                }
                .reduce { _: S, nextState: S ->
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