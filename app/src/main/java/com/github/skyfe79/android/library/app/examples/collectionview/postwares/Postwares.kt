package com.github.skyfe79.android.library.app.examples.collectionview.postwares

import androidx.recyclerview.widget.*
import com.github.skyfe79.android.library.app.examples.collectionview.CollectionState
import com.github.skyfe79.android.library.app.examples.collectionview.CollectionViewModel
import com.github.skyfe79.android.library.app.examples.collectionview.action.LoadAction
import com.github.skyfe79.android.library.app.examples.emojicollection.models.EmojiBoxModel
import com.github.skyfe79.android.library.app.examples.recyclerview.model.TextMessage
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.DefaultSectionModel
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.State
import io.reactivex.Observable

fun CollectionViewModel.makeSectionModels(state: State, action: Action): Observable<State> {
    val collectionState = (state as? CollectionState) ?: return Observable.just(state)

    return when (action) {
        is LoadAction -> {

            val colors = listOf(
                0xffd01774.toInt(),
                0xfff7f93c.toInt(),
                0xfff07777.toInt(),
                0xfffcce62.toInt(),
                0xff58c8d8.toInt()
            )

            val sectionModels = collectionState.emojis.mapIndexed { index, list ->
                val emojiBoxModels = list.map { EmojiBoxModel(it) }
                DefaultSectionModel(
                    emojiBoxModels,
                    header = TextMessage("Section Header #$index", colors[index], true),
                    footer = TextMessage("Section Footer #$index", colors[index], false))
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
           Observable.just(collectionState.copy(sections = sectionModels))
        }
        else -> Observable.just(state)
    }
}