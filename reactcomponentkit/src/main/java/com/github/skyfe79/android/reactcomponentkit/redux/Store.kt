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
        internal fun flow(action: Action): Single<S> {
            return Single.create { emitter ->
                reducerList.forEach { anyValue ->
                    try {
                        val reducer = anyValue as Reducer<STATE, A>
                        val typedAction = action as A
                        val typedState = this@Store.state.copyState() as STATE
                        val mutatedState = reducer(typedState, typedAction) as? S
                        if (mutatedState != null) {
                            this@Store.state = mutatedState
                        }
                    } catch (e: Throwable) {
                        emitter.onError(e)
                        return@create
                    }
                }

                emitter.onSuccess(this@Store.state.copyState() as S)
            }
        }
    }

    lateinit var state: S
        internal set
    private var beforeActionFlow: (S.(Action) -> Action)? = null
    lateinit var actionFlowMap: MutableMap<KClass<*>, Flow<S, Action>>
        private set
    private lateinit var effects: List<Effect<S>>
    private val disposables = CompositeDisposable()

    companion object {
        init {
            RxJavaPlugins.setErrorHandler {}
        }
    }

    /**
     * set initial state for the store
     */
    fun initialState(state: S) {
        this.state = state
        this.actionFlowMap = mutableMapOf()
        this.effects = emptyList()
    }

    /**
     * clean up memories
     */
    fun deinitialize() {
        actionFlowMap = mutableMapOf()
        effects = emptyList()
        disposables.clear()
    }

    /**
     * Setup side effect before flowing actions.
     */
    fun beforeActionFlow(actionFlow: S.(Action) -> Action) {
        this.beforeActionFlow = actionFlow
    }

    @Suppress("UNCHECKED_CAST")
    internal fun startFlow(action: Action): Action {
        return beforeActionFlow?.invoke(this.state.copyState() as S, action) ?: action
    }

    /**
     * Make a flow of reducers for an action
     */
    inline fun <reified A: Action> flow(vararg reducers: Reducer<S, A>) {
        actionFlowMap[A::class] = Flow(reducers.toList())
    }

    /**
     * do some side effect after finishing a flow.
     */
    fun afterStateFlow(vararg effects: Effect<S>) {
        this.effects = effects.toList()
    }

    /**
     * Do something after dispatching new state.
     * For example, reset route on Android
     */
    internal fun doAfterEffects() {
        var mutatedState = state
        effects.forEach {
            mutatedState = it(mutatedState, mutatedState)
        }
        state = mutatedState
    }

    /**
     * dispatch an action to start a flow for it.
     */
    fun dispatch(action: Action): Single<S> {
        return Single.create { single ->
            // reset error
            this@Store.state.error = null

            val actionFlow = actionFlowMap[action::class]
            actionFlow?.let {
                val disposable = it.flow(action)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
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
}