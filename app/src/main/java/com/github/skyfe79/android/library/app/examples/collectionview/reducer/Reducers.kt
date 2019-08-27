package com.github.skyfe79.android.library.app.examples.collectionview.reducer

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.skyfe79.android.library.app.examples.collectionview.CollectionState
import com.github.skyfe79.android.library.app.examples.collectionview.CollectionViewModel
import com.github.skyfe79.android.library.app.examples.collectionview.action.LoadAction
import com.github.skyfe79.android.library.app.examples.emojicollection.models.EmojiBoxModel
import com.github.skyfe79.android.library.app.examples.emojicollection.util.EmojiHelper
import com.github.skyfe79.android.library.app.examples.recyclerview.model.TextMessage
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.DefaultSectionModel
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun CollectionViewModel.loadEmoji(action: Action) = setState { state ->
    when (action) {
        is LoadAction -> {
            val emojiCollection = (1..5)
                .map {
                    (1..(40..80).random()).map { EmojiHelper.emoji }
                }
            state.copy(emojis = emojiCollection)
        }
        else -> state
    }
}

fun CollectionViewModel.makeSectionModels(action: Action) = setState { state ->
    when (action) {
        is LoadAction -> {

            val colors = listOf(
                0xffd01774.toInt(),
                0xfff7f93c.toInt(),
                0xfff07777.toInt(),
                0xfffcce62.toInt(),
                0xff58c8d8.toInt()
            )

            val sectionModels = state.emojis.mapIndexed { index, list ->
                val emojiBoxModels = list.map { EmojiBoxModel(it) }
                DefaultSectionModel(
                    emojiBoxModels,
                    header = TextMessage("Section Header #$index", colors[index], true),
                    footer = TextMessage("Section Footer #$index", colors[index], false)
                )
                { recyclerView ->
                    recyclerView.layoutManager = when(index) {
                        0 -> GridLayoutManager(getApplication(), 8)
                        1 -> GridLayoutManager(getApplication(), 4)
                        2 -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        3 -> GridLayoutManager(getApplication(), 3)
                        else -> LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false)
                    }

                    if (index == 4) {
                        val snapHelper = LinearSnapHelper()
                        snapHelper.attachToRecyclerView(recyclerView)
                    }
                }
            }
            state.copy(sections = sectionModels)
        }
        else -> state
    }
}

fun CollectionViewModel.loadEmoji2(state: CollectionState): CollectionState {
    val emojiCollection = (1..5)
        .map {
            (1..(40..80).random()).map { EmojiHelper.emoji }
        }
    return state.copy(emojis = emojiCollection)
}

fun CollectionViewModel.makeSectionModels2(state: CollectionState): CollectionState {
    val colors = listOf(
        0xffd01774.toInt(),
        0xfff7f93c.toInt(),
        0xfff07777.toInt(),
        0xfffcce62.toInt(),
        0xff58c8d8.toInt()
    )

    val sectionModels = state.emojis.mapIndexed { index, list ->
        val emojiBoxModels = list.map { EmojiBoxModel(it) }
        DefaultSectionModel(
            emojiBoxModels,
            header = TextMessage("Section Header #$index", colors[index], true),
            footer = TextMessage("Section Footer #$index", colors[index], false)
        )
        { recyclerView ->
            recyclerView.layoutManager = when(index) {
                0 -> GridLayoutManager(getApplication(), 8)
                1 -> GridLayoutManager(getApplication(), 4)
                2 -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                3 -> GridLayoutManager(getApplication(), 3)
                else -> LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false)
            }

            if (index == 4) {
                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(recyclerView)
            }
        }
    }
    return state.copy(sections = sectionModels)
}