package com.github.skyfe79.android.library.app.examples.emojicollection.middlewares

import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionState
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionViewModel
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiRoute
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun EmojiCollectionViewModel.reset(state: State, action: Action): Observable<State> {
    val emojiCollectionState = (state as? EmojiCollectionState) ?: return Observable.just(state)
    return Observable.just(emojiCollectionState.copy(route = EmojiRoute.None))
}