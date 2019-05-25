package com.github.skyfe79.android.library.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.github.skyfe79.android.library.app.action.ResetRouteAction
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType
import com.github.skyfe79.android.library.app.redux.routeReducer
import com.github.skyfe79.android.library.app.redux.reset

enum class MainRoute {
    None,
    CounterExample,
    CounterExample2,
    RecyclerViewExample
}
data class MainState(var route: MainRoute): State()

class MainViewModel: RootViewModelType<MainState>() {

    val route: Output<MainRoute> = Output(MainRoute.None)

    override fun setupStore() {
        store.set(
            initialState = MainState(MainRoute.None),
            middlewares = arrayOf(::reset),
            reducers = arrayOf(::routeReducer),
            postwares = arrayOf()
        )
    }

    override fun on(newState: MainState) {
        route.acceptWithoutCompare(newState.route)
    }
}