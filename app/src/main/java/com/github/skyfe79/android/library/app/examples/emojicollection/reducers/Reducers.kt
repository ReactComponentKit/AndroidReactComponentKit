package com.github.skyfe79.android.library.app.examples.emojicollection.reducers

import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionState
import com.github.skyfe79.android.library.app.examples.emojicollection.EmojiCollectionViewModel
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.AddEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.MakeItemModelsAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.RemoveEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.ShuffleEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.util.EmojiHelper
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import java.lang.Exception
import kotlin.random.Random

fun asyncAPI(): Single<Int> {
    return Single.create {
        Thread.sleep(500L)
        it.onSuccess(1)
    }
}

fun EmojiCollectionViewModel.addEmoji(state: State, action: Action): Observable<State> {
    val emojiCollectionState = (state as? EmojiCollectionState) ?: return Observable.just(state)

    return when (action) {
        is AddEmojiAction -> {
            Single.create<State> { emitter ->
                asyncAPI().subscribeBy(
                    onSuccess = {
                        val mutableEmojiList = emojiCollectionState.emojis.toMutableList()
                        val index = if (mutableEmojiList.isEmpty()) 0 else (0 until mutableEmojiList.size).random()
                        mutableEmojiList.add(index, EmojiHelper.emoji)
                        emitter.onSuccess(emojiCollectionState.copy(emojis = mutableEmojiList))
                        nextDispatch(MakeItemModelsAction, applyNewState = true)
                        //nextDispatch(AddEmojiAction(EmojiHelper.emoji), applyNewState = true)
                    },
                    onError = {
                        emitter.onError(it)
                    }
                )
            }.toObservable()

        }
        else -> Observable.just(state)
    }
}


fun EmojiCollectionViewModel.removeEmoji(state: State, action: Action): Observable<State> {
    val emojiCollectionState = (state as? EmojiCollectionState) ?: return Observable.just(state)

    return when (action) {
        is RemoveEmojiAction -> {
            return try {
                val mutableEmojiList = emojiCollectionState.emojis.toMutableList()
                val index = if (mutableEmojiList.isEmpty()) 0 else (0 until mutableEmojiList.size).random()
                mutableEmojiList.removeAt(index)
                val mutatedState = emojiCollectionState.copy(emojis = mutableEmojiList)
                nextDispatch(MakeItemModelsAction, applyNewState = true)
                Observable.just(mutatedState)
            } catch (e: Exception) {
                // ignore
                Observable.just(state)
            }
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
            nextDispatch(MakeItemModelsAction, applyNewState = true)
            Observable.just(mutatedState)
        }
        else -> Observable.just(state)
    }
}