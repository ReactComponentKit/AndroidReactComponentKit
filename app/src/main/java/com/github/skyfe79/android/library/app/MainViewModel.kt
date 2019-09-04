package com.github.skyfe79.android.library.app

import android.app.Application
import android.util.Log
import com.github.skyfe79.android.library.app.action.*
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.library.app.redux.*
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

enum class MainRoute {
    None,
    CounterExample,
    CounterExample2,
    RecyclerViewExample,
    EmojiCollectionExample,
    CollectionViewExample
}
data class MainState(
    val route: MainRoute = MainRoute.None
): State() {
    override fun copyState(): MainState {
        return this.copy()
    }
}

class MainViewModel(application: Application): RCKViewModel<MainState>(application) {

    val route: Output<MainRoute> = Output(MainRoute.None)

    override fun setupStore() {
        initStore { store ->
            store.initialState(MainState())

            store.flow<ClickCounterExampleButtonAction>(
                ::routeToCounterExample
            )
            store.flow<ClickCounterExample2ButtonAction>(
                ::routeToCounterExample2
            )
            store.flow<ClickRecyclerViewExampleButtonAction>(
                ::routeToRecyclerViewExample
            )
            store.flow<ClickEmojiExampleButtonAction>(
                ::routeToEmojiExample
            )
            store.flow<ClickCollectionViewExampleButtonAction>(
                ::routeToCollectionViewExample
            )
        }
    }

    override fun on(newState: MainState) {
        route.accept(newState.route).afterFlow(MainRoute.None)
    }
}