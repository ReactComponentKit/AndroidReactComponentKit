package com.github.skyfe79.android.library.app

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.github.skyfe79.android.library.app.action.ResetRouteAction
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType
import com.github.skyfe79.android.library.app.redux.routeReducer
import com.github.skyfe79.android.library.app.redux.reset

enum class MainRoute {
    None,
    CounterExample,
    CounterExample2
}
data class MainState(var route: MainRoute): State()

class MainViewModel: RootViewModelType<MainState>() {

    val route: Output<MainRoute> = Output(MainRoute.None, ignoreCompareValue = true)

    override fun setupStore() {
        store.set(
            initialState = MainState(MainRoute.None),
            middlewares = arrayOf(::reset),
            reducers = arrayOf(::routeReducer),
            postwares = arrayOf()
        )
    }

    override fun on(newState: MainState) {
        route.accept(newState.route)
    }
}