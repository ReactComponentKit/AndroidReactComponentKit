package com.github.skyfe79.android.library.app.examples.recyclerview.component

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.github.skyfe79.android.library.app.examples.recyclerview.model.TextMessageProvider
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.component.component
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.*

class TextMessageViewComponent(override var token: Token, override var receiveState: Boolean): ViewComponent(token, receiveState) {
    lateinit var textView: TextView

    override fun layout(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams(matchParent, wrapContent)

            textView = textView {
                gravity = Gravity.CENTER
            }.lparams(matchParent, dip(50))
        }
    }

    override fun on(state: State) {
        val item = (state as? TextMessageProvider) ?: return
        updateTextView(item)
    }

    override fun on(item: ItemModel, position: Int) {
        val textMessage = (item as? TextMessageProvider) ?: return
        updateTextView(textMessage)
    }

    private fun updateTextView(textMessage: TextMessageProvider) {
        textView.text = textMessage.message
    }
}

class ProfileViewComponent(override var token: Token, override var receiveState: Boolean): ViewComponent(token, receiveState) {
    private var textMessageViewComponent = TextMessageViewComponent(token, receiveState)

    override fun layout(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams(matchParent, wrapContent)
            component(textMessageViewComponent) {
                lparams(matchParent, wrapContent)
            }
            background = ContextCompat.getDrawable(context, android.R.color.holo_red_dark)
        }
    }

    override fun on(state: State) {
        textMessageViewComponent.on(state)
    }

    override fun on(item: ItemModel, position: Int) {
        textMessageViewComponent.on(item, position)
    }
}