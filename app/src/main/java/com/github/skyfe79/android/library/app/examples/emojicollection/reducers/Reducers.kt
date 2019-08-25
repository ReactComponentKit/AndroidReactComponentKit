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

fun EmojiCollectionViewModel.route(state: EmojiCollectionState, action: Action): EmojiCollectionState {
    return when(action) {
        is ClickEmojiAction -> state.copy(route = EmojiRoute.AlertEmoji(action.emoji))
        else -> state.copy(route = EmojiRoute.None)
    }
}

fun EmojiCollectionViewModel.addEmoji(state: EmojiCollectionState, action: Action): EmojiCollectionState {
    return when (action) {
        is AddEmojiAction -> {
            val mutableEmojiList = state.emojis.toMutableList()
            val index = if (mutableEmojiList.isEmpty()) 0 else (0 until mutableEmojiList.size).random()
            mutableEmojiList.add(index, EmojiHelper.emoji)
            nextDispatch(MakeItemModelsAction, applyNewState = true)
            state.copy(emojis = mutableEmojiList)
        }
        else -> state
    }
}


fun EmojiCollectionViewModel.removeEmoji(state: EmojiCollectionState, action: Action): EmojiCollectionState {
    return when (action) {
        is RemoveEmojiAction -> {
            return try {
                val mutableEmojiList = state.emojis.toMutableList()
                val index = if (mutableEmojiList.isEmpty()) 0 else (0 until mutableEmojiList.size).random()
                mutableEmojiList.removeAt(index)
                val mutatedState = state.copy(emojis = mutableEmojiList)
                nextDispatch(MakeItemModelsAction, applyNewState = true)
                mutatedState
            } catch (e: Exception) {
                throw  e
            }
        }
        else -> state
    }
}

fun EmojiCollectionViewModel.shuffleEmoji(state: EmojiCollectionState, action: Action): EmojiCollectionState {
    return when (action) {
        is ShuffleEmojiAction -> {
            val mutableEmojiList = state.emojis.toMutableList()
            mutableEmojiList.shuffle()
            val mutatedState = state.copy(emojis = mutableEmojiList)
            nextDispatch(MakeItemModelsAction, applyNewState = true)
            mutatedState
        }
        else -> state
    }
}

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