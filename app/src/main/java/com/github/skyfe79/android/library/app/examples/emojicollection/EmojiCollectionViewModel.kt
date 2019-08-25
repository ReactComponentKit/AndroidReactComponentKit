package com.github.skyfe79.android.library.app.examples.emojicollection

import android.app.Application
import android.util.Log
import com.github.skyfe79.android.library.app.examples.emojicollection.reducers.*
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.redux.Error
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType

sealed class EmojiRoute {
    object None: EmojiRoute()
    data class AlertEmoji(val emoji: String): EmojiRoute()
}

data class EmojiCollectionState(
    val emojis: List<String>,
    val itemModels: List<ItemModel>,
    val route: EmojiRoute = EmojiRoute.None
): State()

class EmojiCollectionViewModel(application: Application): RootViewModelType<EmojiCollectionState>(application) {

    val itemModels = Output<List<ItemModel>>(listOf())
    val routes = Output<EmojiRoute>(EmojiRoute.None)

    override fun setupStore() {
        initStore { store ->
            store.set(
                initialState = EmojiCollectionState(listOf(), listOf()),
                reducers = arrayOf(::route, ::addEmoji, ::removeEmoji, ::shuffleEmoji, ::makeItemModels)
            )
        }
    }

    override fun on(newState: EmojiCollectionState) {
        itemModels.accept(newState.itemModels)
        routes.accept(newState.route).afterReset(EmojiRoute.None)
    }

    override fun on(error: Error) {
        Log.d("ERROR", "$error")
    }
}