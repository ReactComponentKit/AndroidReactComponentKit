package com.github.skyfe79.android.reactcomponentkit.viewmodel

/*
abstract class RootViewModelType<S: State>(application: Application): RCKViewModel<S>(application) {
    val token: Token = Token()
    private val newStateEventBus: EventBus<ComponentNewStateEvent> = EventBus(token)
    private val dispatchEventBus: EventBus<ComponentDispatchEvent> = EventBus(token)
    private var previousState: S? = null

    init {
        dispatchEventBus.on { event ->
            when(event) {
                is ComponentDispatchEvent.Dispatch -> this.dispatch(event.action)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun propagate(state: State) {
        val someState = state as? S
        if (previousState != someState) {
            someState?.let {
                newStateEventBus.post(ComponentNewStateEvent.On(it))
            }
        }
        previousState = someState
    }
}
*/