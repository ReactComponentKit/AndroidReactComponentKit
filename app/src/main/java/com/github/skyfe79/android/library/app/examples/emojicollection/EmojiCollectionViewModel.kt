package com.github.skyfe79.android.library.app.examples.emojicollection

import android.app.Application
import android.util.Log
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.AddEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.RemoveEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.ShuffleEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.components.ClickEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.reducers.*
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.redux.*
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel

sealed class EmojiRoute {
    object None: EmojiRoute()
    data class AlertEmoji(val emoji: String): EmojiRoute()
}

data class EmojiCollectionState(
    val emojis: List<String> = emptyList(),
    val itemModels: List<ItemModel> = emptyList(),
    val route: EmojiRoute = EmojiRoute.None
): State() {
    override fun copyState(): EmojiCollectionState {
        return this.copy()
    }
}

class EmojiCollectionViewModel(application: Application): RCKViewModel<EmojiCollectionState>(application) {

    val itemModels = Output<List<ItemModel>>(listOf())
    val routes = Output<EmojiRoute>(EmojiRoute.None)

    override fun setupStore() {
        initStore { store ->

            store.initialState(EmojiCollectionState())
            store.afterFlow({
                it.copy(route = EmojiRoute.None)
            })


            store.flow<ClickEmojiAction>({ state, action ->
                state.copy(route = EmojiRoute.AlertEmoji(action.emoji))
            })

            store.flow<AddEmojiAction>(
                ::addEmoji,
                { state, _ -> makeItemModels(state) }
            )

            store.flow<RemoveEmojiAction>(
                ::removeEmoji,
                { state, _ -> makeItemModels(state) }
            )

            store.flow<ShuffleEmojiAction>(
                ::shuffleEmoji,
                { state, _ -> makeItemModels(state) }
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