package com.github.skyfe79.android.library.app.examples.emojicollection.afters

import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionState
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiRoute

fun reset(state: EmojiCollectionState): EmojiCollectionState {
    return state.copy(route = EmojiRoute.None)
}