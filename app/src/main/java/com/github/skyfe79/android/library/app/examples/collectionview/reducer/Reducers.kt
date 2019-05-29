package com.github.skyfe79.android.library.app.examples.collectionview.reducer

import com.github.skyfe79.android.library.app.examples.collectionview.CollectionState
import com.github.skyfe79.android.library.app.examples.collectionview.CollectionViewModel
import com.github.skyfe79.android.library.app.examples.collectionview.action.LoadAction
import com.github.skyfe79.android.library.app.examples.emojicollection.util.EmojiHelper
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun CollectionViewModel.loadEmoji(state: State, action: Action): Observable<State> {
    val collectionState = (state as? CollectionState) ?: return Observable.just(state)

    return when (action) {
        is LoadAction -> {
            val emojiCollection = (1..5)
                .map {
                    (1..(40..80).random()).map { EmojiHelper.emoji }
                }
            Observable.just(collectionState.copy(emojis = emojiCollection))
        }
        else -> Observable.just(state)
    }
}