package com.github.skyfe79.android.library.app

import android.app.Application
import com.github.skyfe79.android.library.app.action.*
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.library.app.redux.*
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel

enum class MainRoute {
    None,
    CounterExample,
    CounterExample2,
    RecyclerViewExample,
    EmojiCollectionExample,
    CollectionViewExample
}
data class MainState(var route: MainRoute): State() {
    override fun copyState(): MainState {
        return this.copy()
    }
}

class MainViewModel(application: Application): RCKViewModel<MainState>(application) {

    val route: Output<MainRoute> = Output(MainRoute.None)

    override fun setupStore() {
        initStore { store ->
            store.initialState(MainState(MainRoute.None))

            store.flow<ClickCounterExampleButtonAction>(::routeToCounterExample)
            store.flow<ClickCounterExample2ButtonAction>(::routeToCounterExample2)
            store.flow<ClickRecyclerViewExampleButtonAction>(::routeToRecyclerViewExample)
            store.flow<ClickEmojiExampleButtonAction>(::routeToEmojiExample)
            store.flow<ClickCollectionViewExampleButtonAction>({ state, _ ->
                state.copy(route = MainRoute.CollectionViewExample)
            })
        }
    }

    override fun on(newState: MainState) {
        route.accept(newState.route).afterReset(MainRoute.None)
    }
}