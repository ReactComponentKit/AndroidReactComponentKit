package com.github.skyfe79.android.library.app.examples.collectionview

import android.app.Application
import com.github.skyfe79.android.library.app.examples.collectionview.action.LoadAction
import com.github.skyfe79.android.library.app.examples.collectionview.reducer.*
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.DefaultSectionModel
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.Output
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.redux.VoidAction
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RootViewModelType


data class CollectionState(
    var emojis: List<List<String>> = emptyList(),
    var sections: List<DefaultSectionModel> = emptyList()
): State()



class CollectionViewModel(application: Application): RootViewModelType<CollectionState>(application) {

    val sections: Output<List<DefaultSectionModel>> = Output(emptyList())

    override fun setupStore() {
        initStore { store ->
            store.set(
                initialState = CollectionState(),
                reducers = arrayOf(::loadEmoji, ::makeSectionModels)
            )
        }

    }

    override fun beforeDispatch(action: Action): Action = when(action) {
        is LoadAction -> {
            VoidAction
            //if (store.state.emojis.isNotEmpty()) VoidAction else action
        }
        else -> action
    }

    override fun on(newState: CollectionState) {
        sections.accept(newState.sections)
    }
}