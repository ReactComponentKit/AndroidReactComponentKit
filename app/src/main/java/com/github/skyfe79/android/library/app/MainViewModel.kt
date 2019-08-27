package com.github.skyfe79.android.library.app

import android.app.Application
import com.github.skyfe79.android.library.app.action.*
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.library.app.redux.*
import com.github.skyfe79.android.reactcomponentkit.redux.RCKViewModel

enum class MainRoute {
    None,
    CounterExample,
    CounterExample2,
    RecyclerViewExample,
    EmojiCollectionExample,
    CollectionViewExample
}
data class MainState(var route: MainRoute): State()

class MainViewModel(application: Application): RCKViewModel<MainState>(application) {

    val route: Output<MainRoute> = Output(MainRoute.None)

    override fun setupStore() {
        initStore { store ->
            store.set(
                initialState = MainState(MainRoute.None),
                reducers = arrayOf(::routeReducer)
            )

            store.map(ClickCounterExampleButtonAction, ::routeClickCounterExampleButtonAction)
            store.map(ClickCounterExample2ButtonAction, ::routeClickCounterExample2ButtonAction)
            store.map(ClickRecyclerViewExampleButtonAction, ::routeClickRecyclerViewExampleButtonAction)
            store.map(ClickEmojiExampleButtonAction, ::routeClickEmojiExampleButtonAction)
            store.map(ClickCollectionViewExampleButtonAction, { it.copy(route = MainRoute.CollectionViewExample) })
        }
    }

    override fun on(newState: MainState) {
        route.accept(newState.route).afterReset(MainRoute.None)
    }
}