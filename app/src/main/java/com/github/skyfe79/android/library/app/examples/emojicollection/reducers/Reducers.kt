package com.github.skyfe79.android.library.app.examples.emojicollection.reducers

import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionState
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionViewModel
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.AddEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.RemoveEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.ShuffleEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.util.EmojiHelper
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun EmojiCollectionViewModel.addEmoji(state: State, action: Action): Observable<State> {
    val emojiCollectionState = (state as? EmojiCollectionState) ?: return Observable.just(state)

    return when (action) {
        is AddEmojiAction -> {
            val mutableEmojiList = emojiCollectionState.emojis.toMutableList()
            val index = if (mutableEmojiList.isEmpty()) 0 else (0 until mutableEmojiList.size).random()
            mutableEmojiList.add(index, EmojiHelper.emoji)
            val mutatedState = emojiCollectionState.copy(emojis = mutableEmojiList)
            Observable.just(mutatedState)
        }
        else -> Observable.just(state)
    }
}


fun EmojiCollectionViewModel.removeEmoji(state: State, action: Action): Observable<State> {
    val emojiCollectionState = (state as? EmojiCollectionState) ?: return Observable.just(state)

    return when (action) {
        is RemoveEmojiAction -> {
            val mutableEmojiList = emojiCollectionState.emojis.toMutableList()
            val index = if (mutableEmojiList.isEmpty()) 0 else (0 until mutableEmojiList.size).random()
            mutableEmojiList.removeAt(index)
            val mutatedState = emojiCollectionState.copy(emojis = mutableEmojiList)
            Observable.just(mutatedState)
        }
        else -> Observable.just(state)
    }
}

fun EmojiCollectionViewModel.shuffleEmoji(state: State, action: Action): Observable<State> {
    val emojiCollectionState = (state as? EmojiCollectionState) ?: return Observable.just(state)

    return when (action) {
        is ShuffleEmojiAction -> {
            val mutableEmojiList = emojiCollectionState.emojis.toMutableList()
            mutableEmojiList.shuffle()
            val mutatedState = emojiCollectionState.copy(emojis = mutableEmojiList)
            Observable.just(mutatedState)
        }
        else -> Observable.just(state)
    }
}