package com.github.skyfe79.android.library.app.examples.emojicollection.postwares

import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionState
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionViewModel
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.AddEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.MakeItemModelsAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.RemoveEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.ShuffleEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.models.EmojiBoxModel
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun EmojiCollectionViewModel.makeItemModels(state: EmojiCollectionState, action: Action): EmojiCollectionState {
    return when (action) {
        is MakeItemModelsAction -> {
            val emojiBoxModels = state.emojis.map { EmojiBoxModel(it) }
            val mutatedState = state.copy(itemModels = emojiBoxModels)
            mutatedState
        }
        else -> state
    }
}