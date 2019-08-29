package com.github.skyfe79.android.reactcomponentkit.redux

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlin.reflect.KClass


class Store<S: State> {

    inner class Flow<STATE: State, A: Action>(private val reducerList: List<Any>) {

        @Suppress("UNCHECKED_CAST")
        fun flow(action: Action): Single<S> {
            return Single.create { emitter ->
                reducerList.forEach { anyValue ->
                    val reducer = anyValue as Reducer<STATE, A>
                    val typedAction = action as A
                    val typedState = this@Store.state.copyState() as STATE

                    try {
                        val mutatedState = reducer(typedState, typedAction) as S
                        this@Store.state = mutatedState
                    } catch (e: Throwable) {
                        emitter.onError(e)
                    }
                }

                emitter.onSuccess(this@Store.state.copyState() as S)
            }
        }
    }

    lateinit var state: S
        private set
    lateinit var actionFlowMap: MutableMap<KClass<*>, Flow<S, Action>>
        private set
    private lateinit var afters: List<After<S>>
    private val disposables = CompositeDisposable()

    companion object {
        init {
            RxJavaPlugins.setErrorHandler {
            }
        }
    }

//    fun set(
//        initialState: S,
//        afters: Array<After<S>> = emptyArray()) {
//        this.state = initialState
//        this.actionFlowMap = mutableMapOf()
//        this.afters = afters
//    }

    fun initialState(state: S) {
        this.state = state
        this.actionFlowMap = mutableMapOf()
        this.afters = emptyList()
    }

    fun deinitialize() {
        actionFlowMap = mutableMapOf()
        afters = emptyList()
        disposables.clear()
    }

    inline fun <reified A: Action> flow(vararg reducers: Reducer<S, A>) {
        actionFlowMap[A::class] = Flow(reducers.toList())
    }

    fun afterFlow(vararg afters: After<S>) {
        this.afters = afters.toList()
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

            val actionFlow = actionFlowMap[action::class]
            actionFlow?.let {
                val disposable = it.flow(action)
                    .subscribeOn(Schedulers.single())
                    .observeOn(Schedulers.single())
                    .subscribeBy(
                        onSuccess = { newState ->
                            single.onSuccess(newState)
                        },
                        onError = { error ->
                            this@Store.state.error = Error(error, action)
                            single.onSuccess(this@Store.state)
                        }
                    )
                disposables.add(disposable)
            }
        }
    }

    /*

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

 */
}