package com.github.skyfe79.android.library.app.examples.emojicollection

import com.github.skyfe79.android.library.app.examples.emojicollection.postwares.*
import com.github.skyfe79.android.library.app.examples.emojicollection.reducers.addEmoji
import com.github.skyfe79.android.library.app.examples.emojicollection.reducers.removeEmoji
import com.github.skyfe79.android.library.app.examples.emojicollection.reducers.shuffleEmoji
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType


data class EmojiCollectionState(
    val emojis: List<String>,
    val itemModels: List<ItemModel>
): State()

class EmojiCollectionViewModel: RootViewModelType<EmojiCollectionState>() {

    val itemModels = Output<List<ItemModel>>(listOf())

    override fun setupStore() {
        store.set(
            initialState = EmojiCollectionState(listOf(), listOf()),
            middlewares = emptyArray(),
            reducers = arrayOf(::addEmoji, ::removeEmoji, ::shuffleEmoji),
            postwares = arrayOf(::makeItemModels)
        )
    }

    override fun on(newState: EmojiCollectionState) {
        itemModels.accept(newState.itemModels)
    }
}