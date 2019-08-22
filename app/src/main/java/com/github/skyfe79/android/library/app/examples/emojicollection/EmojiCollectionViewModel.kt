package com.github.skyfe79.android.library.app.examples.emojicollection

import com.github.skyfe79.android.library.app.examples.emojicollection.middlewares.route
import com.github.skyfe79.android.library.app.examples.emojicollection.postwares.*
import com.github.skyfe79.android.library.app.examples.emojicollection.reducers.addEmoji
import com.github.skyfe79.android.library.app.examples.emojicollection.reducers.removeEmoji
import com.github.skyfe79.android.library.app.examples.emojicollection.reducers.shuffleEmoji
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
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

class EmojiCollectionViewModel: RootViewModelType<EmojiCollectionState>() {

    val itemModels = Output<List<ItemModel>>(listOf())
    val routes = Output<EmojiRoute>(EmojiRoute.None)

    override fun setupStore() {
        store.set(
            initialState = EmojiCollectionState(listOf(), listOf()),
            middlewares = arrayOf(::route),
            reducers = arrayOf(::addEmoji, ::removeEmoji, ::shuffleEmoji),
            postwares = arrayOf(::makeItemModels)
        )
    }

    override fun on(newState: EmojiCollectionState) {
        itemModels.accept(newState.itemModels)
        routes.accept(newState.route).afterReset(EmojiRoute.None)
    }
}