package com.github.skyfe79.android.library.app.examples.collectionview

import android.app.Application
import com.github.skyfe79.android.library.app.examples.collectionview.action.LoadAction
import com.github.skyfe79.android.library.app.examples.collectionview.reducer.*
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.DefaultSectionModel
import com.github.skyfe79.android.reactcomponentkit.redux.*


data class CollectionState(
    var emojis: List<List<String>> = emptyList(),
    var sections: List<DefaultSectionModel> = emptyList()
): State()



class CollectionViewModel(application: Application): RCKViewModel<CollectionState>(application) {

    val sections: Output<List<DefaultSectionModel>> = Output(emptyList())

    override fun setupStore() {
        initStore { store ->
            store.set(
                initialState = CollectionState()
                //reducers = arrayOf(::loadEmoji, ::makeSectionModels)
            )

            // map action to reducers
            store.map(LoadAction, ::loadEmoji2, ::makeSectionModels2)
            // or
            store.map(LoadAction,
                { state ->
                    //it's reducer
                    state
                },
                {
                    //it's reducer
                    it
                }
            )
        }
    }

    override fun beforeDispatch(action: Action): Action = when(action) {
        is LoadAction -> withState { state ->
            if (state.emojis.isNotEmpty()) VoidAction else action
        }
        else -> action
    }

    override fun on(newState: CollectionState) {
        sections.accept(newState.sections)
    }
}