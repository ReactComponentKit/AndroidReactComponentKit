package com.github.skyfe79.android.library.app.examples.collectionview

import android.app.Application
import com.github.skyfe79.android.library.app.examples.collectionview.action.LoadAction
import com.github.skyfe79.android.library.app.examples.collectionview.reducer.loadEmoji
import com.github.skyfe79.android.library.app.examples.collectionview.reducer.makeSectionModels
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.DefaultSectionModel
import com.github.skyfe79.android.reactcomponentkit.redux.*
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel


data class CollectionState(
    var emojis: List<List<String>> = emptyList(),
    var sections: List<DefaultSectionModel> = emptyList()
): State() {

    override fun copyState(): CollectionState {
        return this.copy()
    }
}



class CollectionViewModel(application: Application): RCKViewModel<CollectionState>(application) {

    val sections: Output<List<DefaultSectionModel>> = Output(emptyList())

    override fun setupStore() {
        initStore { store ->
            store.initialState(CollectionState())
            store.flow<LoadAction>(
                ::loadEmoji,
                { state, _ ->
                    makeSectionModels(state)
                }
            )
        }
    }

    override fun on(newState: CollectionState) {
        sections.accept(newState.sections)
    }
}