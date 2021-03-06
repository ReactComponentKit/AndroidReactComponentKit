package com.github.skyfe79.android.library.app

import android.view.View
import com.github.skyfe79.android.library.app.action.*
import com.github.skyfe79.android.reactcomponentkit.component.LayoutComponent
import com.github.skyfe79.android.reactcomponentkit.dispatcher.dispatch
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.include
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainViewLayout(token: Token)
    : LayoutComponent<MainActivity>(token) {

    /**
     * You can use layout xml file like below.
     */
    override fun createView(ui: AnkoContext<MainActivity>): View {
        val view = ui.include<View>(R.layout.activity_main)

        view.counterExampleButton.onClick {
            dispatch(ClickCounterExampleButtonAction)
        }

        view.counterExample2Button.onClick {
            dispatch(ClickCounterExample2ButtonAction)
        }

        view.recyclerViewExampleButton.onClick {
            dispatch(ClickRecyclerViewExampleButtonAction)
        }

        view.emojiExampleButton.onClick {
            dispatch(ClickEmojiExampleButtonAction)
        }

        view.collectionExampleButton.onClick {
            dispatch(ClickCollectionViewExampleButtonAction)
        }

        return view
    }

    override fun on(state: State) = Unit
}