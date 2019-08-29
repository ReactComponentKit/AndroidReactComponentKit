package com.github.skyfe79.android.library.app.examples.emojicollection.reducers

import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionState
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionViewModel
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiRoute
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.AddEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.MakeItemModelsAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.RemoveEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.ShuffleEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.components.ClickEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.models.EmojiBoxModel
import com.github.skyfe79.android.library.app.examples.emojicollection.util.EmojiHelper
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import java.lang.Exception
import kotlin.random.Random


fun EmojiCollectionViewModel.addEmoji(state: EmojiCollectionState, action: AddEmojiAction): EmojiCollectionState {
        val mutableEmojiList = state.emojis.toMutableList()
        val index = if (mutableEmojiList.isEmpty()) 0 else (0 until mutableEmojiList.size).random()
        mutableEmojiList.add(index, EmojiHelper.emoji)
        return state.copy(emojis = mutableEmojiList)
}


fun EmojiCollectionViewModel.removeEmoji(state: EmojiCollectionState, action: RemoveEmojiAction): EmojiCollectionState {
    try {
        val mutableEmojiList = state.emojis.toMutableList()
        val index = if (mutableEmojiList.isEmpty()) 0 else (0 until mutableEmojiList.size).random()
        mutableEmojiList.removeAt(index)
        return state.copy(emojis = mutableEmojiList)
    } catch (e: Exception) {
        throw  e
    }
}


fun EmojiCollectionViewModel.shuffleEmoji(state: EmojiCollectionState, action: ShuffleEmojiAction): EmojiCollectionState {
    val mutableEmojiList = state.emojis.toMutableList()
    mutableEmojiList.shuffle()
    return state.copy(emojis = mutableEmojiList)
}


fun EmojiCollectionViewModel.makeItemModels(state: EmojiCollectionState): EmojiCollectionState {
    val emojiBoxModels = state.emojis.map { EmojiBoxModel(it) }
    return state.copy(itemModels = emojiBoxModels)
}