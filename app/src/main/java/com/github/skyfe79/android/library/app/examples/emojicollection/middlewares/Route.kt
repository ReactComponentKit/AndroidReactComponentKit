package com.github.skyfe79.android.library.app.examples.emojicollection.middlewares

import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionState
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionViewModel
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiRoute
import com.github.skyfe79.android.library.app.examples.emojicollection.components.ClickEmojiAction
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun EmojiCollectionViewModel.route(state: EmojiCollectionState, action: Action): EmojiCollectionState {
    return when(action) {
        is ClickEmojiAction -> state.copy(route = EmojiRoute.AlertEmoji(action.emoji))
        else -> state.copy(route = EmojiRoute.None)
    }
}